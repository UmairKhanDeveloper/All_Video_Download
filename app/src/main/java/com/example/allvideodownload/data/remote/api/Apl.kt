package com.example.allvideodownload.data.remote.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class apl(
    @SerialName("abr")
    val abr: String?=null,
    @SerialName("acodec")
    val acodec: String?=null,
    @SerialName("age_limit")
    val ageLimit: Int?=null,
    @SerialName("aspect_ratio")
    val aspectRatio: Double?=null,
    @SerialName("asr")
    val asr: Int?=null,
    @SerialName("audio_channels")
    val audioChannels: Int?=null,
    @SerialName("audio_ext")
    val audioExt: String?=null,
    @SerialName("automatic_captions")
    val automaticCaptions: AutomaticCaptions?=null,
    @SerialName("availability")
    val availability: String?=null,
    @SerialName("average_rating")
    val averageRating: String?=null,
    @SerialName("categories")
    val categories: List<String>?=null,
    @SerialName("channel")
    val channel: String?=null,
    @SerialName("channel_follower_count")
    val channelFollowerCount: Int?=null,
    @SerialName("channel_id")
    val channelId: String?=null,
    @SerialName("channel_url")
    val channelUrl: String?=null,
    @SerialName("chapters")
    val chapters: String?=null,
    @SerialName("comment_count")
    val commentCount: Int?=null,
    @SerialName("description")
    val description: String?=null,
    @SerialName("display_id")
    val displayId: String?=null,
    @SerialName("downloader_options")
    val downloaderOptions: DownloaderOptions?=null,
    @SerialName("duration")
    val duration: Int?=null,
    @SerialName("duration_string")
    val durationString: String?=null,
    @SerialName("dynamic_range")
    val dynamicRange: String?=null,
    @SerialName("epoch")
    val epoch: Int?=null,
    @SerialName("ext")
    val ext: String?=null,
    @SerialName("extractor")
    val extractor: String?=null,
    @SerialName("extractor_key")
    val extractorKey: String?=null,
    @SerialName("filesize")
    val filesize: Int?=null,
    @SerialName("filesize_approx")
    val filesizeApprox: Int?=null,
    @SerialName("format")
    val format: String?=null,
    @SerialName("format_id")
    val formatId: String?=null,
    @SerialName("format_note")
    val formatNote: String?=null,
    @SerialName("_format_sort_fields")
    val formatSortFields: List<String>?=null,
    @SerialName("formats")
    val formats: List<Format>?=null,
    @SerialName("fps")
    val fps: Int?=null,
    @SerialName("fulltitle")
    val fulltitle: String?=null,
    @SerialName("_has_drm")
    val hasDrm1: String?=null,
    @SerialName("has_drm")
    val hasDrm: Boolean?=null,
    @SerialName("heatmap")
    val heatmap: List<Heatmap>?=null,
    @SerialName("height")
    val height: Int?=null,
    @SerialName("http_headers")
    val httpHeaders: HttpHeadersX?=null,
    @SerialName("id")
    val id: String?=null,
    @SerialName("is_live")
    val isLive: Boolean?=null,
    @SerialName("language")
    val language: String?=null,
    @SerialName("language_preference")
    val languagePreference: Int?=null,
    @SerialName("like_count")
    val likeCount: Int?=null,
    @SerialName("live_status")
    val liveStatus: String?=null,
    @SerialName("media_type")
    val mediaType: String?=null,
    @SerialName("original_url")
    val originalUrl: String?=null,
    @SerialName("playable_in_embed")
    val playableInEmbed: Boolean?=null,
    @SerialName("playlist")
    val playlist: String?=null,
    @SerialName("playlist_index")
    val playlistIndex: String?=null,
    @SerialName("preference")
    val preference: String?=null,
    @SerialName("protocol")
    val protocol: String?=null,
    @SerialName("quality")
    val quality: Int?=null,
    @SerialName("release_timestamp")
    val releaseTimestamp:String?=null,
    @SerialName("release_year")
    val releaseYear: String?=null,
    @SerialName("requested_subtitles")
    val requestedSubtitles: String?=null,
    @SerialName("resolution")
    val resolution: String?=null,
    @SerialName("source_preference")
    val sourcePreference: Int?=null,
    @SerialName("subtitles")
    val subtitles: Subtitles?=null,
    @SerialName("tags")
    val tags: List<String>?=null,
    @SerialName("tbr")
    val tbr: Double?=null,
    @SerialName("thumbnail")
    val thumbnail: String?=null,
    @SerialName("thumbnails")
    val thumbnails: List<Thumbnail>?=null,
    @SerialName("timestamp")
    val timestamp: Int?=null,
    @SerialName("title")
    val title: String?=null,
    @SerialName("_type")
    val type: String?=null,
    @SerialName("upload_date")
    val uploadDate: String?=null,
    @SerialName("uploader")
    val uploader: String?=null,
    @SerialName("uploader_id")
    val uploaderId: String?=null,
    @SerialName("uploader_url")
    val uploaderUrl: String?=null,
    @SerialName("url")
    val url: String?=null,
    @SerialName("vbr")
    val vbr: String?=null,
    @SerialName("vcodec")
    val vcodec: String?=null,
    @SerialName("_version")
    val version: Version?=null,
    @SerialName("video_ext")
    val videoExt: String?=null,
    @SerialName("view_count")
    val viewCount: Int?=null,
    @SerialName("was_live")
    val wasLive: Boolean?=null,
    @SerialName("webpage_url")
    val webpageUrl: String?=null,
    @SerialName("webpage_url_basename")
    val webpageUrlBasename: String?=null,
    @SerialName("webpage_url_domain")
    val webpageUrlDomain: String?=null,
    @SerialName("width")
    val width: Int?=null
)