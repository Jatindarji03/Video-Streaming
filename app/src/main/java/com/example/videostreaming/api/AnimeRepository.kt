package com.example.videostreaming.api

class AnimeRepository {

    suspend fun getRecentEpisodes() = RetrofitInstance.api.getRecentEpisodes()

    suspend fun getTopAiringEpisodes() = RetrofitInstance.api.getTopAiringEpisodes()

    suspend fun getContentInfo(contentId: String) = RetrofitInstance.api.getContentInfo(contentId)

    suspend fun getEpisodeUrl(episodeId: String) = RetrofitInstance.api.getEpisodeUrl(episodeId)

}