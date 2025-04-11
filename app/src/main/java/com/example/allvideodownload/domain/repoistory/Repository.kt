package com.example.allvideodownload.domain.repoistory

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.room.Insert
import com.example.allvideodownload.data.local.db.Video
import com.example.allvideodownload.data.remote.api.apl
import com.example.allvideodownload.data.remote.apiclient.VideoApiClient
import com.example.allvideodownload.data.remote.apiclient.VideosClient
import com.example.allvideodownload.data.repoistory.VideoDataBase
import io.ktor.util.valuesOf

class Repository(val videoDataBase: VideoDataBase):VideosClient {
    override suspend fun AllVideosDownload(url: String): apl {
      return VideoApiClient.VideosDownload(url)
    }

    fun videoData():LiveData<List<Video>>{
        return videoDataBase.getDao().getAllVideo()
    }

    suspend fun Insert(video: Video){
        return videoDataBase.getDao().Insert(video)
    }

    suspend fun updateDownloadProgress(path: String, progress: Float) {
       return videoDataBase.getDao().updateProgress(path,progress)
    }


}