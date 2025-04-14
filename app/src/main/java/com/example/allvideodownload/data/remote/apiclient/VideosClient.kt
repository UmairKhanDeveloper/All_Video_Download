package com.example.allvideodownload.data.remote.apiclient

import com.example.allvideodownload.data.remote.api.api

interface VideosClient {
    suspend fun AllVideosDownload(url:String): api
}