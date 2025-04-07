package com.example.allvideodownload.data.remote.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DownloaderOptions(
    @SerialName("http_chunk_size")
    val httpChunkSize: Int
)