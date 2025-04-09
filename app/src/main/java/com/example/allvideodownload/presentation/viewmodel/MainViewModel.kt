package com.example.allvideodownload.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allvideodownload.data.local.db.Video
import com.example.allvideodownload.data.remote.api.apl
import com.example.allvideodownload.domain.repoistory.Repository
import com.example.allvideodownload.domain.usecase.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream


class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _allVideos = MutableStateFlow<ResultState<apl>>(ResultState.Loading)
    val allVideos: StateFlow<ResultState<apl>> = _allVideos.asStateFlow()

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


    fun downloadVideoWithProgress(video: Video, url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()

            client.newCall(request).execute().use { response ->
                val body = response.body ?: return@use
                val totalBytes = body.contentLength()
                var downloadedBytes = 0L

                val input = body.byteStream()
                val output = FileOutputStream(File(video.path))

                val buffer = ByteArray(8192)
                var bytes = input.read(buffer)
                while (bytes >= 0) {
                    output.write(buffer, 0, bytes)
                    downloadedBytes += bytes
                    val progress = downloadedBytes / totalBytes.toFloat()
                    video.id?.let { repository.updateVideoProgress(it, progress) }

                    bytes = input.read(buffer)
                }
                output.flush()
                output.close()
            }
        }
    }




}
