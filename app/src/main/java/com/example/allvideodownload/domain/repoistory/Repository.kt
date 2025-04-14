package com.example.allvideodownload.domain.repoistory

import androidx.lifecycle.LiveData
import com.example.allvideodownload.data.local.db.Video
import com.example.allvideodownload.data.remote.api.api
import com.example.allvideodownload.data.remote.apiclient.VideoApiClient
import com.example.allvideodownload.data.remote.apiclient.VideosClient
import com.example.allvideodownload.data.repoistory.VideoDataBase

class Repository(val videoDataBase: VideoDataBase):VideosClient {
    override suspend fun AllVideosDownload(url: String): api {
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