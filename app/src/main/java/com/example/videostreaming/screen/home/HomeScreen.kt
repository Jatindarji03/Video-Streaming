package com.example.videostreaming.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.videostreaming.screen.anime.AnimeScreen
import com.example.videostreaming.screen.comingsoon.ComingSoonScreen
import com.example.videostreaming.ui.theme.Black
import com.example.videostreaming.ui.theme.Blue
import com.example.videostreaming.ui.theme.Gray
import com.example.videostreaming.ui.theme.VideoStreamingTheme


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val items = listOf("Anime", "Coming Soon")
    var selectedChipIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.background(Black)) {
            Text(
                text = "Let's Help You Relax & Watch a Series",
                color = Color.White,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(top = 10.dp, end = 20.dp, start = 15.dp)
            )
            ChipSection(chips = items, onChipSelected = { selectedChipIndex = it })

            when (selectedChipIndex) {
                0 -> AnimeScreen()
                1 -> ComingSoonScreen()
            }
        }

    }
}

@Composable
fun ChipSection(
    chips: List<String>,
    onChipSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedChipIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    LazyRow(modifier = modifier) {
        items(chips.size) {
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                    .clickable {
                        selectedChipIndex = it
                        onChipSelected(it)
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (selectedChipIndex == it) Blue else Gray)
                    .padding(15.dp)
            ) {
                Text(
                    text = chips[it],
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )

            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreensPreview() {
    VideoStreamingTheme {
        HomeScreen()
    }
}