package com.example.allvideodownload.data.remote.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HttpHeadersX(
    @SerialName("Accept")
    val accept: String,
    @SerialName("Accept-Language")
    val acceptLanguage: String,
    @SerialName("Sec-Fetch-Mode")
    val secFetchMode: String,
    @SerialName("User-Agent")
    val userAgent: String
)