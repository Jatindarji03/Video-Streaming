package com.example.videostreaming.screen.anime

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.example.videostreaming.screen.component.CustomImageSwitcher
import com.example.videostreaming.screen.model.Content
import com.example.videostreaming.ui.theme.Black
import com.example.videostreaming.ui.theme.VideoStreamingTheme
import org.json.JSONException

@Composable
fun AnimeScreen(modifier: Modifier = Modifier) {
    val context: Context = LocalContext.current
    var animeList by remember { mutableStateOf<List<Content>>(emptyList()) }
    LaunchedEffect(Unit) {
        fetchAnimeData(context) {
            animeList = it
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Black),

        ) {
        CustomImageSwitcher(animeList)

    }
}

fun fetchAnimeData(context: Context, onResult: (List<Content>) -> Unit) {
    val animeList = mutableListOf<Content>()
    val requestQueue: RequestQueue = Volley.newRequestQueue(context)
    val url = "https://api-opal-one-88.vercel.app/anime/gogoanime/recent-episodes"
    val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
        try {
            val result = response.getJSONArray("results")
            for (i in 0 until result.length()) {
                val content = Content(
                    id = result.getJSONObject(i).getString("id"),
                    title = result.getJSONObject(i).getString("title"),
                    imageUrl = result.getJSONObject(i).getString("image")
                )
                animeList.add(content)
            }
            onResult(animeList)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }, { error ->
        Log.d("data", "error: ${error.message}")
    }
    )
    requestQueue.add(jsonObjectRequest)
}

@Preview(
    showBackground = true, showSystemUi = true
)
@Composable
fun AnimeScreenPreview() {
    VideoStreamingTheme {
        AnimeScreen()
    }
}