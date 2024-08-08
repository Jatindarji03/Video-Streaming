package com.example.videostreaming.screen.displaycontent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videostreaming.api.AnimeRepository
import com.example.videostreaming.screen.model.ContentInfoResponse
import com.example.videostreaming.screen.model.EpisodeResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
                _contentInfo.value = repository.getContentInfo(contentId)
                _episodeUrl.value=repository.getEpisodeUrl(_contentInfo.value!!.episodes.firstOrNull()?.id!!)


            } catch (e: Exception) {

            }
        }
    }
}