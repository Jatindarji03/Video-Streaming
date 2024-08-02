package com.example.videostreaming.screen.contentnfo

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.videostreaming.R
import com.example.videostreaming.ui.theme.Black
import com.example.videostreaming.ui.theme.Blue
import com.example.videostreaming.ui.theme.Gray
import com.example.videostreaming.ui.theme.VideoStreamingTheme

@Composable
fun ContentInfoScreen(modifier: Modifier = Modifier) {
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
        MovieImageCard()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MovieName(title = "Interstellar")
            LikeButton(checked = checked, onCheckedChange = { checked = it })
        }
        SubOrDub(text = "Dub")
        ContentInformation(information = "In 2067, humanity faces extinction due to a global blight. Joseph Cooper, a former NASA test pilot, along with his son and daughter, Tom and Murph, and father-in-law Donald, toil as farmers. One evening during a dust storm, Cooper and Murph discover mysterious patterns in falling particles. Decoding the patterns leads them to a secret NASA facility run by scientist Dr. John Brand. Cooper is enlisted to pilot the spaceship Endurance through a newly-discovered wormhole near Saturn, searching for habitable planets. Cooper struggles with leaving his children behind but decides to do it in the hope of saving Tom and Murph's generation from extinction. He promises Murph he will return, but she is distraught. Cooper joins the Endurance team, consisting of Romilly, Doyle, Brand's daughter Amelia, and the robots TARS and CASE.")
        ContentReleaseInfo(startDate = "2024", status = "ongoing", totalEpisode = "2")
        ListOfCategory(category = listOf("Action", "Horror", "Comedy", "Drama"))
        WatchNowButton()

    }
}

@Composable
fun WatchNowButton(modifier: Modifier=Modifier){
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(Blue),
        modifier = modifier.fillMaxWidth()
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
        Text(text = textDisplay, color = Color.White,modifier=Modifier.padding(start = 2.dp))

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
            .clickable { isExpanded = !isExpanded },
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
        style = MaterialTheme.typography.labelLarge
    )
}

@Composable
fun MovieImageCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(10.dp),
        shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.interstellar),
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
        style = MaterialTheme.typography.displayLarge,
        modifier = modifier
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
        ContentInfoScreen()
    }
}