package com.example.allvideodownload.data.repoistory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.allvideodownload.data.local.dao.VideoDao
import com.example.allvideodownload.data.local.db.Video

@Database(entities = [Video::class], version = 1, exportSchema = false)

abstract class VideoDataBase:RoomDatabase(){
    abstract fun getDao():VideoDao
    companion object {
        @Volatile
        private var INSTANCE: VideoDataBase? = null

        fun getDataBase(context: Context): VideoDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, VideoDataBase::class.java,
                    "notes_database"
                ).build()
                INSTANCE = instance
                instance
            }

        }

    }
}