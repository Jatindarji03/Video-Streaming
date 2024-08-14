package com.example.videostreaming

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp

@Composable
fun DemoScreen() {
    // State to hold the boolean value
    var isClicked by remember { mutableStateOf(false) }

    // Get screen configuration
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val density = LocalDensity.current.density

    // Screen dimensions in dp
    val screenWidth = with(density) { configuration.screenWidthDp.dp }
    val screenHeight = with(density) { configuration.screenHeightDp.dp }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        // Pass the boolean state and dimensions to Container composable
        Container(
            isClicked = isClicked,
            onButtonClick = { isClicked = !isClicked },
            containerWidth = if (isClicked) screenWidth else screenWidth / 2,
            containerHeight = if (isClicked) screenHeight else 200.dp
        )
    }
}

@Composable
fun Container(isClicked: Boolean, onButtonClick: () -> Unit, containerWidth: Dp, containerHeight: Dp) {
    Box(
        modifier = Modifier
            .width(containerWidth)
            .height(containerHeight)
            .background(if (isClicked) Color.Green else Color.Black)
            .padding(16.dp)
    ) {
        Button(onClick = onButtonClick, modifier = Modifier.align(Alignment.Center)) {
            Text(text = if (isClicked) "In Landscape" else "Click me")
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DemoScreenPreview() {
    DemoScreen()
}