package com.example.videostreaming.screen.contentnfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ContentInfoViewModelFactory(private val contentId:String) :ViewModelProvider.Factory {
    override fun <T:ViewModel> create(modelClass:Class<T>):T{
        if(modelClass.isAssignableFrom(ContentInfoViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ContentInfoViewModel(contentId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}