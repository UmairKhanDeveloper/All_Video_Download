package com.example.allvideodownload.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allvideodownload.data.local.db.Video
import com.example.allvideodownload.data.remote.api.api
import com.example.allvideodownload.domain.repoistory.Repository
import com.example.allvideodownload.domain.usecase.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _allVideos = MutableStateFlow<ResultState<api>>(ResultState.Loading)
    val allVideos: StateFlow<ResultState<api>> = _allVideos.asStateFlow()

    val allVideoDAta: LiveData<List<Video>> = repository.videoData()

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

    fun Insert(video: Video) {
        viewModelScope.launch {
            repository.Insert(video)
        }
    }


    fun updateProgress(path: String, progress: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDownloadProgress(path, progress)
        }
    }





}
