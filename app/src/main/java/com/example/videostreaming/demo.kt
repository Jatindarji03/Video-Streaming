package com.example.videostreaming

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.ui.PlayerView
import com.example.videostreaming.screen.displaycontent.BackwardButton
import com.example.videostreaming.screen.displaycontent.ForwardButton
import com.example.videostreaming.screen.displaycontent.FullScreenButton
import com.example.videostreaming.screen.displaycontent.PlayPauseButton
import com.example.videostreaming.screen.displaycontent.formatDuration
import com.example.videostreaming.ui.theme.Black
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DemoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(top = 30.dp)
    ) {
        VideoContainer(videoUri = "https://www111.vipanicdn.net/streamhls/bb147c1422d31e816233969148f86031/ep.6.1723349866.360.m3u8")
        ContentTitle(title = "Spy X Family")
    }
}

@OptIn(UnstableApi::class)
@Composable
fun VideoContainer(videoUri: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(true) }
    var isFullScreen by remember { mutableStateOf(false) }
    var showButton by remember { mutableStateOf(true) }
    var showProgressBar by remember { mutableStateOf(true) }
    var showTime by remember { mutableStateOf(true) }
    var showFullScreenButton by remember { mutableStateOf(true) }
    var currentPosition by remember { mutableLongStateOf(0L) }
    var duration by remember { mutableLongStateOf(0L) }
    val coroutineScope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val screenHeight = with(density) { configuration.screenHeightDp.dp }

    val exoPlayer = remember { ExoPlayer.Builder(context).build() }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    LaunchedEffect(videoUri) {
        val mediaSource = HlsMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(MediaItem.fromUri(videoUri))
        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = isPlaying
    }

    LaunchedEffect(isPlaying) {
        exoPlayer.playWhenReady = isPlaying
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentPosition = exoPlayer.currentPosition
            duration = exoPlayer.duration
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            if (!showButton) {
                showProgressBar = false
                showTime = false
                showFullScreenButton = false
            }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(isFullScreen) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START && isFullScreen) {
                (context as? Activity)?.requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            } else if (event == Lifecycle.Event.ON_STOP) {
                (context as? Activity)?.requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            (context as? android.app.Activity)?.requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    val currentTime = remember(currentPosition) { formatDuration(currentPosition) }
    val formattedDuration = remember(duration) { formatDuration(duration) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(if (isFullScreen) screenHeight else 200.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        showButton = true
                        showProgressBar = true
                        showTime = true
                        showFullScreenButton = true
                        coroutineScope.launch {
                            delay(5000)
                            showButton = false
                            delay(500)
                            showProgressBar = false
                            showTime = false
                            showFullScreenButton = false
                        }
                    }
                )
            }
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    useController = false
                    this.player = exoPlayer
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        if (showFullScreenButton) {
            FullScreenButton(
                isFullScreen = isFullScreen,
                onClick = { isFullScreen = !isFullScreen },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(3.dp)
                    .size(64.dp)
            )
        }

        if (showButton) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackwardButton(onClick = { exoPlayer.seekBack() })
                Spacer(modifier = Modifier.width(16.dp))
                PlayPauseButton(
                    isPlaying = isPlaying,
                    onClick = {
                        isPlaying = !isPlaying
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                ForwardButton(onClick = { exoPlayer.seekForward() })
            }
        }

        if (showTime) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                if (showProgressBar) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(Color.Gray)
                    ) {
                        val progress =
                            (currentPosition.toFloat() / duration.toFloat()).coerceIn(0f, 1f)
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(
                                    (progress * (LocalConfiguration.current.screenWidthDp.dp - 32.dp)).coerceAtLeast(
                                        0.dp
                                    )
                                )
                                .background(Color.Blue)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = currentTime,
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = formattedDuration,
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Composable
fun ContentTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        color = Color.White,
        style = MaterialTheme.typography.displayMedium,
        modifier = modifier.fillMaxWidth(0.9f)
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DemoScreenPreview() {
    DemoScreen()
}