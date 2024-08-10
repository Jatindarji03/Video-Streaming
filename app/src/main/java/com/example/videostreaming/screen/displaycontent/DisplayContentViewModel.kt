package com.example.videostreaming.screen.displaycontent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videostreaming.api.AnimeRepository
import com.example.videostreaming.screen.model.ContentInfoResponse
import com.example.videostreaming.screen.model.EpisodeResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

class DisplayContentViewModel(contentId: String) : ViewModel() {
    private val repository = AnimeRepository()

    private val _contentInfo = MutableStateFlow<ContentInfoResponse?>(null)
    val contentInfo: StateFlow<ContentInfoResponse?> = _contentInfo.asStateFlow()


    private val _episodeUrl = MutableStateFlow<EpisodeResponse?>(null)
    val episodeUrl: StateFlow<EpisodeResponse?> = _episodeUrl.asStateFlow()


    init {
        fetchContentInfo(contentId)
    }

    private fun fetchContentInfo(contentId: String) {
        viewModelScope.launch {
            try {
                val info = repository.getContentInfo(contentId)
                _contentInfo.value = info
                // Optionally fetch the URL for the first episode if needed
                info.episodes.firstOrNull()?.id?.let { episodeId ->
                    fetchEpisodeUrl(episodeId)
                }
            } catch (e: Exception) {
                Log.e("DisplayContentViewModel", "Error fetching content info: ${e.message}")
            }
        }
    }
    fun fetchEpisodeUrl(episodeId: String) {
        viewModelScope.launch {
            try {

                _episodeUrl.value = repository.getEpisodeUrl(episodeId)
            } catch (e: Exception) {
                Log.e("DisplayContentViewModel", "Error fetching episode URL: ${e.message}")
            }
        }
    }
}