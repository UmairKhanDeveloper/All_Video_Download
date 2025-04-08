package com.example.allvideodownload.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allvideodownload.data.remote.api.apl
import com.example.allvideodownload.data.remote.apiclient.VideosClient
import com.example.allvideodownload.domain.repoistory.Repository
import com.example.allvideodownload.domain.usecase.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _allVideos = MutableStateFlow<ResultState<apl>>(ResultState.Loading)
    val allVideos: StateFlow<ResultState<apl>> = _allVideos.asStateFlow()

     fun AllVideoDownloader(url: String) {
        viewModelScope.launch {
            _allVideos.value = ResultState.Loading
            try {
                val response = repository.AllVideosDownload(url)
                _allVideos.value = ResultState.Succses(response)
            } catch (e: Exception) {
                _allVideos.value = ResultState.Error(e)

            }
        }
    }
}