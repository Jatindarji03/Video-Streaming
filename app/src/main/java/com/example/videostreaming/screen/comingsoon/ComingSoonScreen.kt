package com.example.videostreaming.screen.comingsoon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.videostreaming.ui.theme.Black
import com.example.videostreaming.ui.theme.VideoStreamingTheme

@Composable
fun ComingSoonScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Black)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Coming Soon ...",
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge,
            modifier=Modifier.padding(20.dp)
        )

    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ComingSoonScreenPreview() {
    VideoStreamingTheme {
        ComingSoonScreen()
    }
}