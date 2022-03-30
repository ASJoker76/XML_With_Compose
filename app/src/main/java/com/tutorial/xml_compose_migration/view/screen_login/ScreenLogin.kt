package com.tutorial.xml_compose_migration.view.screen_login

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.tutorial.xml_compose_migration.model.ResLogin
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


@Composable
fun ScreenLogin(navController: NavController, loginViewModel: ScreenLoginViewModel, context: Context ) {
    val constraints = ConstraintSet {
        val email = createRefFor("email")
        val password = createRefFor("password")
        val lupa_kata_sandi = createRefFor("lupa_kata_sandi")
        val masuk = createRefFor("masuk")

        constrain(email) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.matchParent
            height = Dimension.wrapContent
        }

        constrain(password) {
            top.linkTo(email.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.matchParent
            height = Dimension.wrapContent
        }

        constrain(lupa_kata_sandi) {
            top.linkTo(password.bottom)
            end.linkTo(parent.end)
            width = Dimension.matchParent
            height = Dimension.wrapContent
        }

        constrain(masuk) {
            top.linkTo(lupa_kata_sandi.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.matchParent
            height = Dimension.wrapContent
        }
    }


    ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
        var text by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }

        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Email") },
            modifier = Modifier
                .layoutId("email")
                .padding(15.dp)
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            placeholder = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, description)
                }
            },
            modifier = Modifier
                .layoutId("password")
                .padding(15.dp)
        )

        Text(
            text = "Lupa Kata Sandi",
            fontSize = 14.sp,
            textAlign = TextAlign.Right,
            modifier = Modifier
                .padding(15.dp)
                .layoutId("lupa_kata_sandi")
        )

        Button(
            onClick = {

                Log.e(TAG, "ScreenLogin: ")

                val jsonObject = JSONObject()
                jsonObject.put("email", text)
                jsonObject.put("password", password)
                jsonObject.put("tipe", "2")

                // Convert JSONObject to String
                val jsonObjectString = jsonObject.toString()

                // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
                val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

                loginViewModel.getLogin(requestBody)

            },
            // Uses ButtonDefaults.ContentPadding by default
            modifier = Modifier
                .padding(15.dp)
                .layoutId("masuk")
        ) {
            // Inner content including an icon and a text label
            //Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Masuk")
        }
    }
}
