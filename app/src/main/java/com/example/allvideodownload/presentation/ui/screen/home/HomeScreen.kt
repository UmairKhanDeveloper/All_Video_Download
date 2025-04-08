package com.example.allvideodownload.presentation.ui.screen.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.StatFs
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.allvideodownload.R
import com.example.allvideodownload.data.remote.api.apl
import com.example.allvideodownload.domain.repoistory.Repository
import com.example.allvideodownload.domain.usecase.ResultState
import com.example.allvideodownload.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen() {
    var textField by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val repository = remember { Repository() }
    val viewModel = remember { MainViewModel(repository) }
    val state by viewModel.allVideos.collectAsState()
    val videoData = remember { mutableStateOf<apl?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    when (state) {
        is ResultState.Error -> {
            isLoading = false
            val error = (state as ResultState.Error).error
            Text(text = "$error")
        }

        ResultState.Loading -> {
            isLoading = true
        }

        is ResultState.Succses -> {
            isLoading = false
            var succses = (state as ResultState.Succses).response
            videoData.value = succses

        }
    }






    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { showBottomSheet = true }) {
                Icon(Icons.Default.Add, contentDescription = "Open Bottom Sheet")
            }
        }
    ) {
        videoData?.value?.let { api ->
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Close",
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE0E0E0))
                                .clickable { showBottomSheet = false }
                                .padding(4.dp),
                            tint = Color.Black
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(contentAlignment = Alignment.Center) {
                                    Image(
                                        painter = rememberAsyncImagePainter(api.thumbnail),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(100.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Fit
                                    )
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "",
                                        tint = Color.White
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = api.title ?: "",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }


                        }
                        Text(
                            text = "RENAME",
                            color = Color(0xFF1E88E5),
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.End)
                                .clickable { }
                        )

                        Divider(thickness = 0.5.dp, color = Color.LightGray)

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Path:/storage/emul...idMate/download/",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "26.5GB FREE/30.0GB",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = "CHANGE",
                                color = Color(0xFF1E88E5),
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp,
                                modifier = Modifier.clickable { }
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(Color.Red),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_music),
                                    contentDescription = "Music Icon",
                                    modifier = Modifier.size(20.dp),
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Music", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }

                        val audioSelectedOption = remember { mutableStateOf("") }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                RadioButtonOptionAudio("128K (MMA)", audioSelectedOption)
                                RadioButtonOptionAudio("128K (MP3)", audioSelectedOption)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                RadioButtonOptionAudio("48K (MP3)", audioSelectedOption)
                                RadioButtonOptionAudio("256K (MP3)", audioSelectedOption)
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(Color.Red),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_videos),
                                    contentDescription = "Video Icon",
                                    modifier = Modifier.size(20.dp),
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Video", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }

                        val videoSelectedOption = remember { mutableStateOf("") }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                RadioButtonOptionAudio("144P (MP4)", videoSelectedOption)
                                RadioButtonOptionAudio("240P (MP4)", videoSelectedOption)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                RadioButtonOptionAudio("360P (MP4)", videoSelectedOption)
                                RadioButtonOptionAudio("480P (MP4)", videoSelectedOption)
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                RadioButtonOptionAudio("720P HD (MP4)", videoSelectedOption)
                                RadioButtonOptionAudio("1080P HD (MP4)", videoSelectedOption)
                            }
                        }

                        Button(
                            onClick = { },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text(text = "Download", color = Color.White, fontSize = 16.sp)
                        }
                    }
                }
            }


        }
        Scaffold(topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = "Video Download", fontWeight = FontWeight.Bold)
            }, navigationIcon = {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "")
            })
        }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(top = it.calculateTopPadding()),
            ) {

                TextField(
                    value = textField,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
                    onValueChange = { textField = it },
                    placeholder = {
                        Text(text = "Paste your link here", color = Color.Gray)
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Attachment, contentDescription = "")
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color(0xFFE7E7E7),
                        unfocusedContainerColor = Color(0xFFE7E7E7)
                    ),
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        showBottomSheet = true
                        viewModel.AllVideoDownloader(textField)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(150.dp)
                        .height(40.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0XFFfb727c))
                ) {
                    Text(text = "Download", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Social Media",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    val context = LocalContext.current

                    SocialMediaIcon(icon = R.drawable.ic_youtube, label = "YouTube") {
                        val url = "https://www.youtube.com/"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }

                    SocialMediaIcon(icon = R.drawable.ic_tiktok, label = "TikTok") {
                        val url = "https://www.tiktok.com/"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }

                    SocialMediaIcon(icon = R.drawable.ic_instagram, label = "Instagram") {
                        val url = "https://www.instagram.com/"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }

                    SocialMediaIcon(icon = R.drawable.ic_facebook, label = "Facebook") {
                        val url = "https://www.facebook.com/"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun SocialMediaIcon(icon: Int, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clickable { onClick() }
                .clip(RoundedCornerShape(12.dp))
                .border(1.5.dp, Color(0xFFD1D1D1), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "$label Icon",
                modifier = Modifier.size(32.dp)
            )
        }

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            ),
            color = Color(0xFF616161)
        )
    }
}


@Composable
fun RadioButtonOptionAudio(option: String, selectedOption: MutableState<String>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { selectedOption.value = option }
            .padding(vertical = 4.dp)
    ) {
        RadioButton(
            selected = selectedOption.value == option,
            onClick = { selectedOption.value = option },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.Red,
                unselectedColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = option,
            fontSize = 12.sp,
            color = if (selectedOption.value == option) Color.Black else Color.Gray
        )
    }
}

fun getStorageInfo(): Pair<String, String> {
    val stat = StatFs(Environment.getExternalStorageDirectory().absolutePath)
    val bytesAvailable = stat.availableBytes
    val bytesTotal = stat.totalBytes

    val gbAvailable = bytesAvailable.toDouble() / (1024 * 1024 * 1024)
    val gbTotal = bytesTotal.toDouble() / (1024 * 1024 * 1024)

    val free = String.format("%.1fGB", gbAvailable)
    val total = String.format("%.1fGB", gbTotal)

    return Pair(free, total)
}
