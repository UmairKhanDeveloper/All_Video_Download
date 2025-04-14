package com.example.allvideodownload.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.allvideodownload.data.local.db.Video

@Dao
interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun Insert(video: Video)

    @Query("SELECT * FROM Video ORDER BY id DESC")
    fun getAllVideo(): LiveData<List<Video>>

    @Query("UPDATE video SET downloadProgress = :progress WHERE path = :path")
    suspend fun updateProgress(path: String, progress: Float)



}