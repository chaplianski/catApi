package com.example.thecatapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.thecatapi.APIService
import com.example.thecatapi.data.PostDataSource

class MainViewModel(private val apiService: APIService) : ViewModel() {

    val flow = Pager(PagingConfig(pageSize = 8)) {
        PostDataSource(apiService)
    }.flow.cachedIn(viewModelScope)

}