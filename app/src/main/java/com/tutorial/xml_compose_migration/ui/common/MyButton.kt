package com.tutorial.xml_compose_migration.ui.common

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.tutorial.xml_compose_migration.ui.navigation.NavigationScreen

@Composable
fun NavigationButton(
    navController: NavController,
    destination: NavigationScreen,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {
            Log.i(destination.name, "Navigate to screen 1")
            navController.navigate(destination.name)
        },
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = "Go to ${destination.title}")
    }
}