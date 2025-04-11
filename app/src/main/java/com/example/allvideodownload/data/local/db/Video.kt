package com.example.allvideodownload.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Video(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val url: String,
    val Image: String,
    val path: String,
    var downloadProgress: Float = 0f
)

