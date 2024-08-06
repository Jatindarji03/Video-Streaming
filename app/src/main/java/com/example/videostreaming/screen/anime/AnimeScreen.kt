package com.example.videostreaming.screen.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.videostreaming.screen.component.CustomImageSwitcher
import com.example.videostreaming.screen.model.Content
import com.example.videostreaming.ui.theme.Black
import com.example.videostreaming.ui.theme.VideoStreamingTheme

@Composable
fun AnimeScreen(navController: NavController,modifier: Modifier = Modifier) {

    val viewModel: AnimeViewModel = viewModel()
    val recentEpisodes by viewModel.recentEpisodes.collectAsState()
    val topAiringEpisodes by viewModel.topAiringEpisodes.collectAsState()
    var state = rememberScrollState()


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Black)
            .verticalScroll(state),

        ) {
        CustomImageSwitcher(contentList = recentEpisodes, onClick = {
            navController.navigate("content_info/${recentEpisodes[it].id}")
        })
        Text(
            text = "Top Airing \uD83D\uDD25",
            color = Color.White,
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            style = MaterialTheme.typography.headlineLarge
        )
        LazyRow {
            items(topAiringEpisodes.size) { index ->
                DisplayContentCard(navController,content = topAiringEpisodes[index])
            }
        }


    }
}

@Composable
fun DisplayContentCard(navController: NavController,content: Content, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(start = 16.dp).clickable {
            navController.navigate("content_info/${content.id}")
        },
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .height(230.dp)
                .width(175.dp)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = content.image,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = content.title,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }


        }

    }
}


@Preview(
    showBackground = true, showSystemUi = true
)
@Composable
fun AnimeScreenPreview() {
    VideoStreamingTheme {
        AnimeScreen(NavController(LocalContext.current))
    }
}