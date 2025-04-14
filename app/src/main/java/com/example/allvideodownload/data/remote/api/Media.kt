package com.example.allvideodownload.data.remote.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Media(
    @SerialName("audioQuality")
    val audioQuality: String? = null,
    @SerialName("audioSampleRate")
    val audioSampleRate: String? = null,
    @SerialName("bitrate")
    val bitrate: Int? = null,
    @SerialName("duration")
    val duration: Int? = null,
    @SerialName("ext")
    val ext: String? = null,
    @SerialName("extension")
    val extension: String? = null,
    @SerialName("formatId")
    val formatId: Int? = null,
    @SerialName("fps")
    val fps: Int? = null,
    @SerialName("height")
    val height: Int? = null,
    @SerialName("is_audio")
    val isAudio: Boolean? = null,
    @SerialName("label")
    val label: String? = null,
    @SerialName("mimeType")
    val mimeType: String? = null,
    @SerialName("quality")
    val quality: String,
    @SerialName("type")
    val type: String,
    @SerialName("url")
    val url: String,
    @SerialName("width")
    val width: Int? = null
)
