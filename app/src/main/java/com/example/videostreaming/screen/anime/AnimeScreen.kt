package com.example.videostreaming.screen.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.videostreaming.screen.component.CustomImageSwitcher
import com.example.videostreaming.ui.theme.Black
import com.example.videostreaming.ui.theme.VideoStreamingTheme

@Composable
fun AnimeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Black),

        ) {
        CustomImageSwitcher("Anime")

    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AnimeScreenPreview() {
    VideoStreamingTheme {
        AnimeScreen()
    }
}