package com.example.videostreaming.screen.anime

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videostreaming.api.AnimeRepository
import com.example.videostreaming.screen.model.Content
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeViewModel : ViewModel() {
    private val repository = AnimeRepository()

    private val _recentEpisodes = MutableStateFlow<List<Content>>(emptyList())
    val recentEpisodes: StateFlow<List<Content>> = _recentEpisodes.asStateFlow()

    private val _topAiringEpisodes = MutableStateFlow<List<Content>>(emptyList())
    val topAiringEpisodes: StateFlow<List<Content>> = _topAiringEpisodes.asStateFlow()


    init {
        fetchRecentEpisodes()
        fetchTopAiringEpisodes()
    }

    private fun fetchRecentEpisodes() {
        viewModelScope.launch {
            try {
                val response = repository.getRecentEpisodes()
                _recentEpisodes.value = response.results
            } catch (e: Exception) {
                Log.e("AnimeViewModel", "Error fetching recent episodes: ${e.message}")
            }
        }
    }
    private fun fetchTopAiringEpisodes() {
        viewModelScope.launch {
            try {
                val response = repository.getTopAiringEpisodes()
                _topAiringEpisodes.value = response.results
            } catch (e: Exception) {

            }
        }
    }

}