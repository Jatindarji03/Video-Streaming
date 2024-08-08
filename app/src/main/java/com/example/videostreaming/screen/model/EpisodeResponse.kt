package com.example.videostreaming.screen.model

data class EpisodeResponse(
    val headers: Headers,
    val sources: List<Source>,
    val download: String
)

data class Headers(
    val Referer: String
)

data class Source(
    val url: String,
    val isM3U8: Boolean,
    val quality: String
)