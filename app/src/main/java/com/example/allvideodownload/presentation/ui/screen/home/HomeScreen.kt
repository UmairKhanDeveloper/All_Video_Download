package com.example.allvideodownload.presentation.ui.screen.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.WindowInsetsAnimation
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.allvideodownload.R
import com.example.allvideodownload.data.local.db.Video
import com.example.allvideodownload.data.remote.api.apl
import com.example.allvideodownload.data.repoistory.VideoDataBase
import com.example.allvideodownload.domain.repoistory.Repository
import com.example.allvideodownload.domain.usecase.ResultState
import com.example.allvideodownload.presentation.ui.navigation.Screen
import com.example.allvideodownload.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.buffer
import okio.sink
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun HomeScreen(navController: NavController) {
    var textField by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current
    val videoDataBase = VideoDataBase.getDataBase(context)
    val repository = remember { Repository(videoDataBase) }
    val viewModel = remember { MainViewModel(repository) }
    val state by viewModel.allVideos.collectAsState()
    val videoData = remember { mutableStateOf<apl?>(null) }
    var isLoading by remember { mutableStateOf(false) }


    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                Log.d("Permission", "WRITE_EXTERNAL_STORAGE granted")
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("Permission", "WRITE_EXTERNAL_STORAGE denied")
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    fun checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            when {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    Log.d("Permission", "WRITE_EXTERNAL_STORAGE already granted")
                    Toast.makeText(context, "Permission already granted", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Log.d("Permission", "Requesting WRITE_EXTERNAL_STORAGE")
                    requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
        } else {

        }
    }

    LaunchedEffect(Unit) {
        checkAndRequestPermissions()
    }


    when (state) {
        is ResultState.Error -> {
            isLoading = false
            val error = (state as ResultState.Error).error
            Log.e("API", "Error fetching video data: $error")
            Text(text = "$error")
        }

        ResultState.Loading -> {
            isLoading = true
            Log.d("API", "Loading video data...")
        }

        is ResultState.Succses -> {
            isLoading = false
            val succses = (state as ResultState.Succses).response
            videoData.value = succses
            Log.d("API", "Successfully fetched video data: $succses")
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { showBottomSheet = true }) {
                Icon(Icons.Default.Add, contentDescription = "Open Bottom Sheet")
            }
        }
    ) { innerPadding ->
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
                            .verticalScroll(rememberScrollState())
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
                            onClick = {
                                if (videoSelectedOption.value.isNotEmpty()) {
                                    checkAndRequestPermissions()

                                    val selectedVideoQuality = videoSelectedOption.value
                                    val selectedAudioQuality = audioSelectedOption.value

                                    viewModel.Insert(
                                        Video(
                                            id = null,
                                            title = api.title ?: "",
                                            url = api.url ?: "",
                                            Image = api.thumbnail ?: "",
                                            path = api.id ?: "",
                                            downloadProgress = 0.100f
                                        )
                                    )

                                    val videoUrl = api.url ?: return@Button
                                    val fileName = api.title
                                    downloadFile(context, videoUrl, title = fileName, mimeType = "video/mp4")


                                    Log.e("url", videoUrl)

                                    navController.navigate(Screen.ProgressScreen.route)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please select video quality",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
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
        }) { innerPadding ->  // Access innerPadding here
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding) // Use innerPadding
                    .padding(16.dp),
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

private fun downloadFile(context: Context, url: String, title: String?, mimeType: String) {
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    val uri = Uri.parse(url)
    val request = DownloadManager.Request(uri)
        .setMimeType(mimeType)
        .setTitle(title)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$title.$mimeType")

    downloadManager.enqueue(request)
}