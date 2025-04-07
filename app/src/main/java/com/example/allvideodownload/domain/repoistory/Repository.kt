package com.example.allvideodownload.domain.repoistory

import com.example.allvideodownload.data.remote.apiclient.VideoApiClient
import com.example.allvideodownload.data.remote.apiclient.VideosClient
import io.ktor.util.valuesOf

class Repository():VideosClient {
    override suspend fun AllVideosDownload(): VideosClient {
      return VideoApiClient.VideosDownload()
    }
}