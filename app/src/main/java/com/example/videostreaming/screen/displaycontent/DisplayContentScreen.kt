package com.example.videostreaming.screen.displaycontent

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.ui.PlayerView
import com.example.videostreaming.R
import com.example.videostreaming.ui.theme.Black
import com.example.videostreaming.ui.theme.VideoStreamingTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DisplayContentScreen(modifier: Modifier = Modifier) {
    val state = rememberScrollState()

    Column(
        modifier = modifier
            .background(Black)
            .fillMaxSize()
            .verticalScroll(state)
    ) {
//        DisplayVideo(
//            uri = "https://www111.vipanicdn.net/streamhls/0789fd4f049c3ca2a49b860ea5d1f456/ep.1.1709225406.360.m3u8",
//            modifier = Modifier
//                .height(200.dp)
//                .fillMaxWidth()
//        )
        VideoContainer(
            videoUri = "https://www111.vipanicdn.net/streamhls/0789fd4f049c3ca2a49b860ea5d1f456/ep.1.1709225406.360.m3u8"
        )
    }

}

@OptIn(UnstableApi::class)
@Composable
fun Video(uri: String, onFullscreenToggle: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    var isFullscreen by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .background(Color.Black)
            .height(200.dp)
            .fillMaxWidth()
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    useController = false
                    val exoPlayer = ExoPlayer.Builder(context).build().also { player ->
                        val mediaSource = HlsMediaSource.Factory(DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(uri))
                        player.setMediaSource(mediaSource)
                        player.prepare()
                        player.playWhenReady = true
                    }
                    this.player = exoPlayer
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = {
                isFullscreen = !isFullscreen
                onFullscreenToggle(isFullscreen)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = if (isFullscreen) Icons.Filled.ThumbUp else Icons.Filled.Search,
                contentDescription = if (isFullscreen) "Exit Fullscreen" else "Fullscreen",
                tint = Color.White
            )
        }

    }

}

@OptIn(UnstableApi::class)
@Composable
fun VideoContainer(videoUri: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(true) }
    var showButton by remember { mutableStateOf(true) }
    var showProgressBar by remember { mutableStateOf(true) }
    var showTime by remember { mutableStateOf(true) }
    var showFullScreenButton by remember { mutableStateOf(true) }
    var currentPosition by remember { mutableLongStateOf(0L) }
    var duration by remember { mutableLongStateOf(0L) }
    val coroutineScope = rememberCoroutineScope()

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaSource = HlsMediaSource.Factory(DefaultHttpDataSource.Factory())
                .createMediaSource(MediaItem.fromUri(videoUri))
            setMediaSource(mediaSource)
            prepare()
            playWhenReady = isPlaying
        }
    }

    // Update playWhenReady based on isPlaying
    LaunchedEffect(isPlaying) {
        exoPlayer.playWhenReady = isPlaying
    }

    // Update currentPosition and duration periodically
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000) // Update every second
            currentPosition = exoPlayer.currentPosition
            duration = exoPlayer.duration
        }
    }

    // Coroutine to hide UI elements after 5 seconds
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

    // Calculate formatted time
    val currentTime = remember(currentPosition) { formatDuration(currentPosition) }
    val formattedDuration = remember(duration) { formatDuration(duration) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        showButton = true
                        showProgressBar = true
                        showTime = true
                        showFullScreenButton = true
                        coroutineScope.launch {
                            delay(5000) // Hide UI elements after 5 seconds of interaction
                            showButton = false
                            delay(500) // Short delay before hiding progress bar and time texts
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
                    useController = false // Hide default controls
                    this.player = exoPlayer
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Display the fullscreen button at the top right corner
        if (showFullScreenButton) {
            IconButton(
                onClick = { /* No action for now */ },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(3.dp)
                    .size(64.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.fullscreen), // Use appropriate icon
                    contentDescription = "Fullscreen",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        // Display the Button only if showButton is true
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

        // Display the current time and duration below the progress bar
        if (showTime) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                // Progress Bar
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

                // Time Display
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
fun BackwardButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier.size(64.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.backward),
            contentDescription = "Backward 10 Seconds",
            tint = Color.White,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun ForwardButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier.size(64.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.forward),
            contentDescription = "Forward 10 Seconds ",
            tint = Color.White,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun PlayPauseButton(isPlaying: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier.size(64.dp)) {
        Icon(
            painter = painterResource(id = if (isPlaying) R.drawable.pause else R.drawable.play),
            contentDescription = if (isPlaying) "Pause" else "Play",
            tint = Color.White,
            modifier = Modifier.size(40.dp)
        )
    }
}

// Function to format duration
fun formatDuration(durationMs: Long): String {
    val totalSeconds = durationMs / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}
//@Composable
//fun DisplayVideo(uri: String, modifier: Modifier = Modifier) {
//    val context = LocalContext.current
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            setMediaItem(MediaItem.fromUri(Uri.parse(uri)))
//            prepare()
//        }
//    }
//    androidx.compose.ui.viewinterop.AndroidView(
//        factory = {
//            PlayerView(context).apply {
//                player = exoPlayer
//            }
//        },
//        modifier = modifier
//    )
//}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DisplayContentScreenPreview() {
    VideoStreamingTheme {
        DisplayContentScreen()
    }
}