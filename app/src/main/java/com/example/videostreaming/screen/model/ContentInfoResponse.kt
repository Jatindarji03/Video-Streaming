package com.example.videostreaming.screen.model

data class ContentInfoResponse(
    val id: String,
    val title: String,
    val url: String,
    val genres: List<String>,
    val totalEpisodes: Int,
    val image: String,
    val releaseDate: String,
    val description: String,
    val subOrDub: String,
    val type: String,
    val status: String,
    val otherName: String,
    val episodes: List<Episodes>
)
