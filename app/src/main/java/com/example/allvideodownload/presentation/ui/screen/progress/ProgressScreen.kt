package com.example.allvideodownload.presentation.ui.screen.progress

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.example.allvideodownload.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProgressScreen() {
    val context = LocalContext.current
    val videoDataBase = remember { VideoDataBase.getDataBase(context) }
    val repository = remember { Repository(videoDataBase) }
    val viewModel = remember { MainViewModel(repository) }


    val videos by viewModel.allVideoDAta.observeAsState(emptyList())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = "PROGRESS VIDEOS", fontWeight = FontWeight.SemiBold)
            })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (videos.isEmpty()) {
                item {
                    Text(text = "No videos downloaded", color = Color.Gray)
                }
            } else {
                items(videos) { video ->
                    ProgressVideosCard(video = video)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}




@Composable
fun ProgressVideosCard(video: Video) {
    val context = LocalContext.current
    val videoDataBase = remember { VideoDataBase.getDataBase(context) }
    val repository = remember { Repository(videoDataBase) }
    val viewModel = remember { MainViewModel(repository) }
    Card(
        modifier = Modifier
            .height(120.dp)
            .width(400.dp),
        elevation = CardDefaults.elevatedCardElevation(focusedElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = rememberAsyncImagePainter(video.Image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Fit
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_play),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = video.title,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Color.LightGray, CircleShape)
                                    .clickable { },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    modifier = Modifier.size(16.dp),
                                    tint = Color.Gray
                                )
                            }
                        }


                        Box(contentAlignment = Alignment.Center) {
                            LinearProgressIndicator(
                                color = Color(0XFFfc6a7f),
                                progress = video.downloadProgress,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .clip(CircleShape)
                            )
                            Text(
                                text = "${(video.downloadProgress * 100).toInt()}%",
                                fontSize = 8.sp,
                                color = Color.White
                            )
                        }


                        Text(
                            text = "Download",
                            fontSize = 8.sp,
                            modifier = Modifier.align(Alignment.End),
                            color = Color(0XFF767676)
                        )
                    }
                }
            }
        }
    }
}
