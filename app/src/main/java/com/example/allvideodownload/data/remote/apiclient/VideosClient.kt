package com.example.allvideodownload.data.remote.apiclient

import com.example.allvideodownload.data.remote.api.apl

interface VideosClient {
    suspend fun AllVideosDownload(url:String):apl
}