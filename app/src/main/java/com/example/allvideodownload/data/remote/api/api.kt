package com.example.allvideodownload.data.remote.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class api(
    @SerialName("author")
    val author: String?=null,
    @SerialName("duration")
    val duration: Int?=null,
    @SerialName("error")
    val error: Boolean?=null,
    @SerialName("medias")
    val medias: List<Media>?=null,
    @SerialName("source")
    val source: String?=null,
    @SerialName("thumbnail")
    val thumbnail: String?=null,
    @SerialName("time_end")
    val timeEnd: Int?=null,
    @SerialName("title")
    val title: String?=null,
    @SerialName("type")
    val type: String?=null,
    @SerialName("url")
    val url: String?=null
)