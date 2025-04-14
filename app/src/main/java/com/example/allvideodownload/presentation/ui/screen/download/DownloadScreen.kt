package com.example.allvideodownload.presentation.ui.screen.download

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.allvideodownload.R
import com.example.allvideodownload.data.local.db.Video
import com.example.allvideodownload.data.repoistory.VideoDataBase
import com.example.allvideodownload.domain.repoistory.Repository
import com.example.allvideodownload.presentation.ui.screen.progress.ProgressVideosCard
import com.example.allvideodownload.presentation.viewmodel.MainViewModel
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadScreen() {
    val context = LocalContext.current
    val videoDataBase = remember { VideoDataBase.getDataBase(context) }
    val repository = remember { Repository(videoDataBase) }
    val viewModel = remember { MainViewModel(repository) }

    val allVideos by viewModel.allVideoDAta.observeAsState(emptyList())
    val downloadedVideos = allVideos.filter { it.downloadProgress == 1f }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "DOWNLOAD VIDEOS", fontWeight = FontWeight.SemiBold)
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (downloadedVideos.isEmpty()) {
                item {
                    Text(text = "No videos downloaded", color = Color.Gray)
                }
            } else {
                items(downloadedVideos) { video ->
                    DownloadedVideosCard(video = video)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}





@Composable
fun DownloadedVideosCard(
    video: Video,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = {  }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(86.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(video.Image),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.Black.copy(alpha = 0.4f), shape = CircleShape)
                        .padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = video.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Download completed",
                    fontSize = 12.sp,
                    color = Color(0xFF6D6D6D)
                )
            }

            IconButton(
                onClick = {  },
                modifier = Modifier
                    .align(Alignment.Top)
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options",
                    tint = Color(0xFFfc6a7f)
                )
            }
        }
    }
}
