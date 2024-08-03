package com.example.videostreaming.api

import com.example.videostreaming.screen.model.AnimeResponse
import com.example.videostreaming.screen.model.ContentInfoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeApiService {
    @GET("anime/gogoanime/recent-episodes")
    suspend fun getRecentEpisodes(): AnimeResponse

    @GET("anime/gogoanime/top-airing")
    suspend fun getTopAiringEpisodes(): AnimeResponse

    @GET("anime/gogoanime/info/{contentId}")
    suspend fun getContentInfo(@Path("contentId") contentId: String): ContentInfoResponse

}
