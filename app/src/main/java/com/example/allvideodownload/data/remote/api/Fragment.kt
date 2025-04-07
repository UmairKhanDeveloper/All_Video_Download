package com.example.allvideodownload.data.remote.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Fragment(
    @SerialName("duration")
    val duration: Double,
    @SerialName("url")
    val url: String
)