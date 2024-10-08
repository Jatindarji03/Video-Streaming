package com.example.videostreaming.screen.displaycontent

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.videostreaming.R
import com.example.videostreaming.screen.model.EpisodeResponse
import com.example.videostreaming.ui.theme.Black
import com.example.videostreaming.ui.theme.Blue
import com.example.videostreaming.ui.theme.Gray
import com.example.videostreaming.ui.theme.VideoStreamingTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun DisplayContentScreen(id: String?, modifier: Modifier = Modifier) {

    val context = LocalContext.current
    // Ensure this is within a Composable context
    val viewModel: DisplayContentViewModel =
        viewModel(factory = DisplayContentViewModelFactory(id!!))

    // Collect state within a composable
    val contentInfo by viewModel.contentInfo.collectAsState()
    val episodeUrl by viewModel.episodeUrl.collectAsState()

    var isExpanded by remember { mutableStateOf(false) }
    var selectedEpisodeIndex by remember { mutableIntStateOf(0) }

    var showSheet by remember { mutableStateOf(false) }

    var isVideoPlaying by remember { mutableStateOf(true) }  // State to control video playback

    var currentVideoUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(episodeUrl) {
        currentVideoUrl = episodeUrl?.sources?.first()?.url
        Log.d("episodeUri", "Uri  $currentVideoUrl")
    }

    if (showSheet) {
        BottomSheet(episodeUrl,
            onQualityChanged = {
                currentVideoUrl = it
            }, onDismiss = { showSheet = false })
    }

    Column(
        modifier = modifier
            .background(Black)
            .fillMaxSize()
    ) {
        currentVideoUrl?.let { videoUrl ->
            VideoContainer(videoUri = videoUrl, onPause = { isVideoPlaying = false })
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        contentInfo?.let {
                            ContentTitle(
                                title = it.title,
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                        ExpendMoreLessButton(
                            isExpanded = isExpanded,
                            onCheckedChange = { isExpanded = !isExpanded }
                        )
                    }
                }

                item {
                    contentInfo?.let { ContentReleaseDate(releaseYear = it.releaseDate) }
                }

                if (isExpanded) {
                    contentInfo?.let {
                        item {
                            ContentRelatedInfo(
                                status = it.status,
                                type = it.type,
                                totalEpisode = it.totalEpisodes.toString()
                            )
                        }
                        item {
                            ContentDescription(description = it.description)
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp, bottom = 5.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CustomButton(
                            hint = "360 P",
                            icon = R.drawable.quailty,
                            onClick = { showSheet = true },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 20.dp) // Ensure the button takes equal width
                        )
                        Spacer(modifier = Modifier.width(8.dp))  // Add spacing between the buttons
                        CustomButton(
                            hint = "Download",
                            icon = R.drawable.download,
                            onClick = {
                                isVideoPlaying = false
                                val intent =
                                    Intent(Intent.ACTION_VIEW, Uri.parse(episodeUrl?.download))
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 20.dp) // Ensure the button takes equal width
                        )
                    }

                }


                contentInfo?.let { info ->
                    items(info.totalEpisodes) { index ->
                        EpisodeContainer(
                            imageUri = info.image,
                            value = "Episode ${index + 1}",
                            subOrDub = info.subOrDub,
                            isSelected = selectedEpisodeIndex == index,
                            onClick = {
                                selectedEpisodeIndex = index
                                val episodeId = info.episodes[selectedEpisodeIndex].id
                                viewModel.fetchEpisodeUrl(episodeId)
                            }
                        )
                    }
                }
            }
        }
    }

}

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    episodeUri: EpisodeResponse?,
    onDismiss: () -> Unit,
    onQualityChanged: (String) -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = Color.DarkGray
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()  // Make sure the Column fills the width
                .padding(vertical = 7.dp)
        ) {
            // Ensure each button stretches across the width
            QualityChangeButton(
                hint = "1080p",
                onClick = {
                    episodeUri?.sources?.get(3)?.url?.let { onQualityChanged(it) }
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))  // Add spacing between buttons
            QualityChangeButton(
                hint = "720p",
                onClick = {
                    episodeUri?.sources?.get(2)?.url?.let { onQualityChanged(it) }
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))  // Add spacing between buttons
            QualityChangeButton(
                hint = "480p",
                onClick = {
                    episodeUri?.sources?.get(1)?.url?.let { onQualityChanged(it) }
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))  // Add spacing between buttons
            QualityChangeButton(
                hint = "360p",
                onClick = {
                    episodeUri?.sources?.first()?.url?.let { onQualityChanged(it) }
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun QualityChangeButton(hint: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick, colors = ButtonDefaults.buttonColors(
            containerColor = Blue,
            contentColor = Black
        ),
        shape = RoundedCornerShape(8.dp),  // Square shape
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 12.dp
        ),  // Adjust padding as needed
        modifier = modifier
    ) {
        Text(
            text = hint,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun CustomButton(
    hint: String,
    icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue,
            contentColor = Black
        ),
        shape = RoundedCornerShape(8.dp),  // Square shape
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 12.dp
        ),  // Adjust padding as needed
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)  // Adjust icon size as needed
        )
        Spacer(modifier = Modifier.width(12.dp))  // Increased space between icon and text
        Text(
            text = hint,
            style = MaterialTheme.typography.labelLarge
        )
    }
}


@Composable
fun ContentDescription(description: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(Gray),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Text(
            text = description,
            modifier = Modifier.padding(5.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )

    }
}

@Composable
fun ContentRelatedInfo(
    status: String,
    type: String,
    totalEpisode: String,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier = modifier.padding(10.dp)) {
        items(1) {
            ContentInfoContainer(hint = "Status", value = status)
            ContentInfoContainer(hint = "Type", value = type)
            ContentInfoContainer(hint = "Total Episode", value = totalEpisode)

        }
    }

}

@Composable
fun ContentInfoContainer(hint: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(top = 10.dp, bottom = 10.dp, end = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Gray)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = hint, style = MaterialTheme.typography.labelLarge, color = Color.White)
            Text(text = value, style = MaterialTheme.typography.headlineSmall, color = Color.White)
        }

    }
}


@OptIn(UnstableApi::class)
@Composable
fun VideoContainer(videoUri: String, onPause: () -> Unit, modifier: Modifier = Modifier) {
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

    // Call the onPause callback when the video is paused
    LaunchedEffect(isPlaying) {
        if (!isPlaying) {
            onPause()
        }
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

@Composable
fun FullScreenButton(isFullScreen: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            painter = painterResource(id = if (isFullScreen) R.drawable.fullscreen_exit else R.drawable.fullscreen),
            contentDescription = null,
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

@Composable
fun ContentTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        color = Color.White,
        style = MaterialTheme.typography.displayMedium,
        modifier = modifier.fillMaxWidth(0.9f)
    )
}

@Composable
fun ExpendMoreLessButton(
    isExpanded: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = onCheckedChange
    ) {
        Icon(
            modifier = Modifier.height(100.dp),
            imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            tint = Color.White
        )
    }

}

@Composable
fun ContentReleaseDate(releaseYear: String, modifier: Modifier = Modifier) {
    Text(
        text = releaseYear,
        style = MaterialTheme.typography.displaySmall,
        color = Color.White,
        modifier = modifier.padding(start = 10.dp, end = 10.dp)
    )
}

@Composable
fun EpisodeContainer(
    imageUri: String,
    value: String,
    subOrDub: String,
    isSelected: Boolean, // New parameter to check if the episode is selected
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(70.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) Blue else Gray) // Change color based on selection
            .clickable(onClick = onClick) // Make the entire Box clickable
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp) // Make width and height equal to make it square
                    .clip(RoundedCornerShape(10.dp)) // Apply rounded corners
                    .background(Color.Gray) // Optional: background color if the image is not loaded
            ) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop, // Ensures the image covers the Box without distortion
                    modifier = Modifier.fillMaxSize() // Ensure the image fills the Box
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .weight(1f) // Takes up available space
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center, // Center items vertically
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = value,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = subOrDub,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterVertically) // Center the image vertically
            ) {
                Image(
                    painter = painterResource(id = if (isSelected) R.drawable.play_circle_black else R.drawable.play_circle),
                    contentDescription = null
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DisplayContentScreenPreview() {
    VideoStreamingTheme {
        DisplayContentScreen("1")
    }
}