package com.example.videostreaming.screen.contentnfo

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.videostreaming.ui.theme.Black
import com.example.videostreaming.ui.theme.Blue
import com.example.videostreaming.ui.theme.Gray
import com.example.videostreaming.ui.theme.VideoStreamingTheme

@Composable
fun ContentInfoScreen(id: String?, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val viewModel: ContentInfoViewModel = viewModel(factory = ContentInfoViewModelFactory(id!!))
    val contentInfo by viewModel.contentInfo.collectAsState()
    var checked by remember {
        mutableStateOf(false)
    }
    val state = rememberScrollState()

    Column(
        modifier = modifier
            .background(Black)
            .fillMaxSize()
            .verticalScroll(state),
    ) {
        MovieImageCard(contentInfo?.image)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            contentInfo?.let { MovieName(title = it.title) }
            LikeButton(checked = checked, onCheckedChange = { checked = it })
        }
        contentInfo?.let { SubOrDub(text = it.subOrDub) }
        contentInfo?.let { ContentInformation(information = it.description) }
        contentInfo?.let {
            ContentReleaseInfo(
                startDate = it.releaseDate,
                status = it.status,
                totalEpisode = it.totalEpisodes.toString()
            )
        }
        contentInfo?.let { ListOfCategory(category = it.genres) }
        WatchNowButton(onClick)


    }
}

@Composable
fun WatchNowButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(Blue),
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
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

@Composable
fun ContentReleaseInfo(
    startDate: String,
    status: String,
    totalEpisode: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(start = 10.dp, top = 10.dp)) {
        ContentReleaseText(textHint = "Start Date:", textDisplay = startDate)
        ContentReleaseText(textHint = "Status:", textDisplay = status)
        ContentReleaseText(textHint = "Total Episode:", textDisplay = totalEpisode)
    }
}

@Composable
fun ContentReleaseText(textHint: String, textDisplay: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(text = textHint, color = Color.White)
        Text(text = textDisplay, color = Color.White, modifier = Modifier.padding(start = 2.dp))

    }
}

@Composable
fun ListOfCategory(category: List<String>, modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier.padding(start = 10.dp, end = 10.dp)) {
        items(category.size) {
            Box(
                modifier = Modifier
                    .padding(top = 10.dp, end = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Gray)
                    .padding(10.dp)
            ) {
                Text(
                    text = category[it],
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge
                )

            }
        }

    }
}

@Composable
fun ContentInformation(information: String, modifier: Modifier = Modifier) {
    // State to track whether the card is expanded or not
    var isExpanded by remember { mutableStateOf(false) }

    // Card with dynamic height and animation
    Card(
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .clickable { isExpanded = !isExpanded }
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(Gray),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .animateContentSize(spring())
                .heightIn(
                    min = 100.dp,
                    max = if (isExpanded) Dp.Infinity else 100.dp
                ) // Animate height change
        ) {
            Text(
                text = if (isExpanded || information.length <= 100) information else "${
                    information.take(
                        100
                    )
                }...",
                color = Color.White,
                modifier = Modifier.padding(),
                style = MaterialTheme.typography.bodyLarge
            )

            // Conditionally show 'Read More' or 'Read Less' text based on the expansion state
            if (information.length > 100) {
                Text(
                    text = if (isExpanded) "Read Less" else "Read More",
                    color = Blue,
                    modifier = Modifier
                        .clickable { isExpanded = !isExpanded }
                        .padding(top = 5.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun SubOrDub(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = Color.White,
        modifier = modifier.padding(start = 10.dp),
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
fun MovieImageCard(imageUri: String? = null, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(10.dp),
        shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = imageUri,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

    }
}

@Composable
fun MovieName(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        color = Color.White,
        style = MaterialTheme.typography.displayMedium,
        modifier = modifier.fillMaxWidth(0.9f)
    )
}

@Composable
fun LikeButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    IconToggleButton(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange
    ) {
        Icon(
            modifier = Modifier.height(100.dp),
            imageVector = if (checked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = null,
            tint = if (checked) Color.Red else Color.White
        )
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ContentInfoScreenPreview() {
    VideoStreamingTheme {
//        ContentInfoScreen()
    }
}