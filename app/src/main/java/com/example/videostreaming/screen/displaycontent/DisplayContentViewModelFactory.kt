package com.example.videostreaming.screen.displaycontent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DisplayContentViewModelFactory(private val contentId: String) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass:Class<T>):T{
        if(modelClass.isAssignableFrom(DisplayContentViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return DisplayContentViewModel(contentId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}