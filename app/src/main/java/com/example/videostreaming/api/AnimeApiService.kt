package com.example.videostreaming.api

import com.example.videostreaming.screen.model.Content
import retrofit2.http.GET

interface AnimeApiService {
    @GET("anime/gogoanime/recent-episodes")
    suspend fun getRecentEpisodes():AnimeResponse

    @GET("anime/gogoanime/top-airing")
    suspend fun getTopAiringEpisodes():AnimeResponse


}

data class AnimeResponse(
    val currentPage: Int,
    val hasNextPage: Boolean,
    val results: List<Content>
)