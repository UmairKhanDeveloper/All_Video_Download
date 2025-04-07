package com.example.allvideodownload.data.remote.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class apl(
    @SerialName("abr")
    val abr: String,
    @SerialName("acodec")
    val acodec: String,
    @SerialName("age_limit")
    val ageLimit: Int,
    @SerialName("aspect_ratio")
    val aspectRatio: Double,
    @SerialName("asr")
    val asr: Int,
    @SerialName("audio_channels")
    val audioChannels: Int,
    @SerialName("audio_ext")
    val audioExt: String,
    @SerialName("automatic_captions")
    val automaticCaptions: AutomaticCaptions,
    @SerialName("availability")
    val availability: String,
    @SerialName("average_rating")
    val averageRating: String,
    @SerialName("categories")
    val categories: List<String>,
    @SerialName("channel")
    val channel: String,
    @SerialName("channel_follower_count")
    val channelFollowerCount: Int,
    @SerialName("channel_id")
    val channelId: String,
    @SerialName("channel_url")
    val channelUrl: String,
    @SerialName("chapters")
    val chapters: String,
    @SerialName("comment_count")
    val commentCount: Int,
    @SerialName("description")
    val description: String,
    @SerialName("display_id")
    val displayId: String,
    @SerialName("downloader_options")
    val downloaderOptions: DownloaderOptions,
    @SerialName("duration")
    val duration: Int,
    @SerialName("duration_string")
    val durationString: String,
    @SerialName("dynamic_range")
    val dynamicRange: String,
    @SerialName("epoch")
    val epoch: Int,
    @SerialName("ext")
    val ext: String,
    @SerialName("extractor")
    val extractor: String,
    @SerialName("extractor_key")
    val extractorKey: String,
    @SerialName("filesize")
    val filesize: Int,
    @SerialName("filesize_approx")
    val filesizeApprox: Int,
    @SerialName("format")
    val format: String,
    @SerialName("format_id")
    val formatId: String,
    @SerialName("format_note")
    val formatNote: String,
    @SerialName("_format_sort_fields")
    val formatSortFields: List<String>,
    @SerialName("formats")
    val formats: List<Format>,
    @SerialName("fps")
    val fps: Int,
    @SerialName("fulltitle")
    val fulltitle: String,
    @SerialName("_has_drm")
    val hasDrm1: String?,
    @SerialName("has_drm")
    val hasDrm: Boolean,
    @SerialName("heatmap")
    val heatmap: List<Heatmap>,
    @SerialName("height")
    val height: Int,
    @SerialName("http_headers")
    val httpHeaders: HttpHeadersX,
    @SerialName("id")
    val id: String,
    @SerialName("is_live")
    val isLive: Boolean,
    @SerialName("language")
    val language: String?,
    @SerialName("language_preference")
    val languagePreference: Int,
    @SerialName("like_count")
    val likeCount: Int,
    @SerialName("live_status")
    val liveStatus: String,
    @SerialName("media_type")
    val mediaType: String?,
    @SerialName("original_url")
    val originalUrl: String,
    @SerialName("playable_in_embed")
    val playableInEmbed: Boolean,
    @SerialName("playlist")
    val playlist: String?,
    @SerialName("playlist_index")
    val playlistIndex: String?,
    @SerialName("preference")
    val preference: String?,
    @SerialName("protocol")
    val protocol: String,
    @SerialName("quality")
    val quality: Int,
    @SerialName("release_timestamp")
    val releaseTimestamp:String?,
    @SerialName("release_year")
    val releaseYear: String?,
    @SerialName("requested_subtitles")
    val requestedSubtitles: String?,
    @SerialName("resolution")
    val resolution: String,
    @SerialName("source_preference")
    val sourcePreference: Int,
    @SerialName("subtitles")
    val subtitles: Subtitles,
    @SerialName("tags")
    val tags: List<String>,
    @SerialName("tbr")
    val tbr: Double,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("thumbnails")
    val thumbnails: List<Thumbnail>,
    @SerialName("timestamp")
    val timestamp: Int,
    @SerialName("title")
    val title: String,
    @SerialName("_type")
    val type: String,
    @SerialName("upload_date")
    val uploadDate: String,
    @SerialName("uploader")
    val uploader: String,
    @SerialName("uploader_id")
    val uploaderId: String,
    @SerialName("uploader_url")
    val uploaderUrl: String,
    @SerialName("url")
    val url: String,
    @SerialName("vbr")
    val vbr: String,
    @SerialName("vcodec")
    val vcodec: String,
    @SerialName("_version")
    val version: Version,
    @SerialName("video_ext")
    val videoExt: String,
    @SerialName("view_count")
    val viewCount: Int,
    @SerialName("was_live")
    val wasLive: Boolean,
    @SerialName("webpage_url")
    val webpageUrl: String,
    @SerialName("webpage_url_basename")
    val webpageUrlBasename: String,
    @SerialName("webpage_url_domain")
    val webpageUrlDomain: String,
    @SerialName("width")
    val width: Int
)