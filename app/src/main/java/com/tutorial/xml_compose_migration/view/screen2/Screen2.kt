package com.tutorial.xml_compose_migration.view.screen2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.tutorial.xml_compose_migration.R
import com.tutorial.xml_compose_migration.model.ResMovie

import com.tutorial.xml_compose_migration.ui.common.MyScreenTitle
import com.tutorial.xml_compose_migration.ui.common.NavigationButton
import com.tutorial.xml_compose_migration.ui.navigation.NavigationScreen


@Composable
fun Screen2(navController: NavController, screen2ViewModel: Screen2ViewModel,movieList: List<ResMovie>) {
    val constraints = ConstraintSet {
        val greenBox = createRefFor("greenbox")
        val redBox = createRefFor("redbox")
        val gambar = createRefFor("gambar")
        val judul = createRefFor("judul")

        constrain(judul){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }

        constrain(gambar){
            top.linkTo(judul.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.value(250.dp)
            height = Dimension.value(250.dp)
        }

        constrain(greenBox) {
            top.linkTo(gambar.bottom)
            start.linkTo(parent.start)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
        constrain(redBox) {
            top.linkTo(gambar.bottom)
            start.linkTo(greenBox.end)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.value(100.dp)
        }

    }


    ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Agus",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
                .layoutId("judul")
        )

        Image(
            painter = rememberImagePainter(
                data = movieList.get(0).imageUrl,

                builder = {
                    scale(Scale.FILL)
                    placeholder(R.drawable.ic_launcher_background)
                    transformations(CircleCropTransformation())

                }
            ),
            contentDescription = movieList.get(0).desc,
            modifier = Modifier
                .padding(vertical = 24.dp)
                .fillMaxHeight()
                .layoutId("gambar")
        )

        Box (modifier = Modifier
            .background(Color.Green)
            .layoutId("greenbox"))
        Box (
            modifier = Modifier
                .background(Color.Red)
                .layoutId("redbox")
        )

        Button(
            onClick =
            {

            },
            modifier = Modifier
                .requiredSize(56.dp)
                .padding(2.dp)
        ) {
            Text(
                text = "HAHAHAH",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}