package com.example.allvideodownload

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.allvideodownload.data.local.db.Video
import com.example.allvideodownload.presentation.ui.navigation.NavEntry
import com.example.allvideodownload.ui.theme.AllVideoDownloadTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AllVideoDownloadTheme {

                NavEntry()
            }
        }

    }
}

