package com.example.videostreaming.screen.model

data class AnimeResponse(
    val currentPage: Int,
    val hasNextPage: Boolean,
    val results: List<Content>
)
