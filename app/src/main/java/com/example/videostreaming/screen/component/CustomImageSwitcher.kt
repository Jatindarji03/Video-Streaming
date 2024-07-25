package com.example.videostreaming.screen.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.videostreaming.R
import com.example.videostreaming.ui.theme.Blue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomImageSwitcher(contentType: String, modifier: Modifier = Modifier) {
    val image = listOf(
        R.drawable.hotd,
        R.drawable.demonslayer,
        R.drawable.interstellar
    )
    val pagerState = rememberPagerState {
        image.size
    }

    HorizontalPager(modifier = modifier, state = pagerState) {


        Card(
            modifier = Modifier
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier.height(400.dp)) {
                Image(
                    modifier = Modifier
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