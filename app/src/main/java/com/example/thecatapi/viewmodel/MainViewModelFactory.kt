package com.example.thecatapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thecatapi.APIService

class MainViewModelFactory(private val apiService: APIService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}