package com.example.videostreaming.screen.contentnfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videostreaming.api.AnimeRepository
import com.example.videostreaming.screen.model.ContentInfoResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContentInfoViewModel(contentId:String) : ViewModel() {
    private val repository = AnimeRepository()

    private val _contentInfo=MutableStateFlow<ContentInfoResponse?>(null)
    val contentInfo:StateFlow<ContentInfoResponse?> = _contentInfo.asStateFlow()

    init {
        fetchContentInfo(contentId)
    }

    private fun fetchContentInfo(contentId:String){
        viewModelScope.launch {
            try{
                _contentInfo.value=repository.getContentInfo(contentId)
            }catch (e:Exception){

            }
        }
    }
}