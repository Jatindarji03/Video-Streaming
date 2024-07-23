package com.example.videostreaming.screen.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.videostreaming.R
import com.example.videostreaming.ui.theme.Black
import com.example.videostreaming.ui.theme.Blue
import com.example.videostreaming.ui.theme.Gray
import com.example.videostreaming.ui.theme.VideoStreamingTheme


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val items = listOf("Movies", "Series", "Anime")
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.background(Black)) {
            Text(
                text = "Let's Help You Relax & Watch a Series",
                color = Color.White,
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(top = 10.dp, end = 20.dp, start = 15.dp)
            )
            ChipSection(chips = items)
            MovieImageSwitcher()
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieImageSwitcher(modifier: Modifier = Modifier) {

    val image = listOf(
        R.drawable.hotd,
        R.drawable.demonslayer,
        R.drawable.interstellar
    )
    val pagerState = rememberPagerState {
        image.size
    }

    HorizontalPager(state = pagerState) {
        val pageOffSet = (pagerState.currentPage - it) + pagerState.currentPageOffsetFraction
        val imageSize by animateFloatAsState(
            targetValue = if (pageOffSet != 0.0f) 0.75f else 1f,
            animationSpec = tween(durationMillis = 300)
        )


        Card(
            modifier = Modifier
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier.height(400.dp)) {
                Image(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = imageSize
                            scaleY = imageSize
                        }
                        .fillMaxSize(),
                    painter = painterResource(id = image[it]),
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
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "House Of The Dragon",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(Blue),
                        ) {
                            Row {
                                Icon(
                                    imageVector = Icons.Filled.PlayArrow,
                                    contentDescription = null
                                )
                                Text(text = "Watch Now")
                            }
                        }

                    }
                }
            }
        }
    }

}


@Composable
fun ChipSection(
    chips: List<String>,
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