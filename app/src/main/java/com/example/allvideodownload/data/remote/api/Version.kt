package com.example.allvideodownload.data.remote.api


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Version(
    @SerialName("current_git_head")
    val currentGitHead: String,
    @SerialName("release_git_head")
    val releaseGitHead: String,
    @SerialName("repository")
    val repository: String,
    @SerialName("version")
    val version: String
)