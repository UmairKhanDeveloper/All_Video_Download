package com.example.allvideodownload.data.remote.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Thumbnail(
    @SerialName("height")
    val height: Int?,
    @SerialName("id")
    val id: String,
    @SerialName("preference")
    val preference: Int,
    @SerialName("resolution")
    val resolution: String?,
    @SerialName("url")
    val url: String,
    @SerialName("width")
    val width: Int?
)