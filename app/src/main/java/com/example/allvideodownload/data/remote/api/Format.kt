package com.example.allvideodownload.data.remote.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Format(
    @SerialName("abr")
    val abr: Double?,
    @SerialName("acodec")
    val acodec: String?,
    @SerialName("aspect_ratio")
    val aspectRatio: Double?,
    @SerialName("asr")
    val asr: Int?,
    @SerialName("audio_channels")
    val audioChannels: Int?,
    @SerialName("audio_ext")
    val audioExt: String,
    @SerialName("columns")
    val columns: Int?,
    @SerialName("container")
    val container: String?,
    @SerialName("downloader_options")
    val downloaderOptions: DownloaderOptions?,
    @SerialName("dynamic_range")
    val dynamicRange: String?,
    @SerialName("ext")
    val ext: String,
    @SerialName("filesize")
    val filesize: Int?,
    @SerialName("filesize_approx")
    val filesizeApprox: Int?,
    @SerialName("format")
    val format: String,
    @SerialName("format_id")
    val formatId: String,
    @SerialName("format_index")
    val formatIndex: String?,
    @SerialName("format_note")
    val formatNote: String?,
    @SerialName("fps")
    val fps: Double?,
    @SerialName("fragments")
    val fragments: List<Fragment>?,
    @SerialName("has_drm")
    val hasDrm: Boolean?,
    @SerialName("height")
    val height: Int?,
    @SerialName("http_headers")
    val httpHeaders: String,
    @SerialName("language")
    val language: String?,
    @SerialName("language_preference")
    val languagePreference: Int?,
    @SerialName("manifest_url")
    val manifestUrl: String?,
    @SerialName("preference")
    val preference: String?,
    @SerialName("protocol")
    val protocol: String,
    @SerialName("quality")
    val quality: Int?,
    @SerialName("resolution")
    val resolution: String,
    @SerialName("rows")
    val rows: Int?,
    @SerialName("source_preference")
    val sourcePreference: Int?,
    @SerialName("tbr")
    val tbr: Double?,
    @SerialName("url")
    val url: String,
    @SerialName("vbr")
    val vbr: Double?,
    @SerialName("vcodec")
    val vcodec: String,
    @SerialName("video_ext")
    val videoExt: String,
    @SerialName("width")
    val width: Int?
)