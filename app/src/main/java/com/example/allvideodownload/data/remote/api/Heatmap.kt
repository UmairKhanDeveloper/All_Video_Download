package com.example.allvideodownload.data.remote.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Heatmap(
    @SerialName("end_time")
    val endTime: Double,
    @SerialName("start_time")
    val startTime: Double,
    @SerialName("value")
    val value: Double
)