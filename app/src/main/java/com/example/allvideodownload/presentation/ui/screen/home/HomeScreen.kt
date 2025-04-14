package com.example.allvideodownload.presentation.ui.screen.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
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
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.allvideodownload.data.remote.api.api
import com.example.allvideodownload.data.repoistory.VideoDataBase
import com.example.allvideodownload.domain.repoistory.Repository
import com.example.allvideodownload.domain.usecase.ResultState
import com.example.allvideodownload.presentation.ui.navigation.Screen
import com.example.allvideodownload.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch

data class MediaOption(val quality: String, val url: String, val ext: String, val type: String)

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
    val videoData = remember { mutableStateOf<api?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


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
                        val audioOptions = mutableListOf<MediaOption>()

                        api.medias?.filter { it.type == "audio" }?.forEach { media ->
                            audioOptions.add(MediaOption(media.quality ?: "Unknown", media.url ?: "", media.ext ?: "", media.type))
                        }

                        val audioSelectedOption = remember { mutableStateOf("") }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp)
                        ) {
                            if (audioOptions.isNotEmpty()) {
                                audioOptions.chunked(2).forEach { rowOptions ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceAround
                                    ) {
                                        rowOptions.forEach { option ->
                                            RadioButtonOptionAudio(option.quality, audioSelectedOption)
                                        }
                                        if (rowOptions.size == 1) {
                                            Spacer(modifier = Modifier.weight(1f))
                                        }
                                    }
                                }
                            } else {
                                Text("No audio options available")
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

                        val videoOptions = mutableListOf<MediaOption>()

                        api.medias?.filter { it.type == "video" }?.forEach { media ->
                            videoOptions.add(MediaOption(media.quality ?: "Unknown", media.url ?: "", media.ext ?: "", media.type))
                        }


                        val videoSelectedOption = remember { mutableStateOf("") }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp)
                        ) {
                            if (videoOptions.isNotEmpty()) {
                                videoOptions.chunked(2).forEach { rowOptions ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceAround
                                    ) {
                                        rowOptions.forEach { option ->
                                            RadioButtonOptionAudio(option.quality, videoSelectedOption)
                                        }
                                        if (rowOptions.size == 1) {
                                            Spacer(modifier = Modifier.weight(1f))
                                        }
                                    }
                                }
                            } else {
                                Text("No video options available")
                            }
                        }


                        Button(
                            onClick = {
                                val selectedVideo = videoOptions.find { it.quality == videoSelectedOption.value }
                                val selectedAudio = audioOptions.find { it.quality == audioSelectedOption.value }

                                val selectedMedia = selectedVideo ?: selectedAudio

                                if (selectedMedia != null) {
                                    val downloadUrl = selectedMedia.url
                                    val fileName = api.title + "." + selectedMedia.ext

                                    coroutineScope.launch {
                                        downloadFile(
                                            context = context,
                                            url = downloadUrl,
                                            title = fileName,
                                            mimeType = if (selectedMedia.type == "video") "video/mp4" else "audio/mp3",
                                            viewModel = viewModel,
                                            apl = api
                                        )
                                    }

                                    navController.navigate(Screen.ProgressScreen.route)
                                } else {
                                    Toast.makeText(context, "Please select quality", Toast.LENGTH_SHORT).show()
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
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Video Download",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {  }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(20.dp)
            ) {
                // Link Input Field
                OutlinedTextField(
                    value = textField,
                    onValueChange = {textField=it},
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    placeholder = {
                        Text(text = "Paste your link here", color = Color.Gray)
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Link, contentDescription = "Link")
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF1F1F1),
                        unfocusedContainerColor = Color(0xFFF1F1F1),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Download Button
                Button(
                    onClick = {
                       showBottomSheet=true
                        viewModel.AllVideoDownloader(textField)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(180.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0XFFfb727c))
                ) {
                    Text(
                        text = "Download",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(36.dp))


                Text(
                    text = "Social Media",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val openLink: (String) -> Unit = { url ->
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    }

                    SocialMediaIcon(R.drawable.ic_youtube, "YouTube") {
                        openLink("https://www.youtube.com/")
                    }

                    SocialMediaIcon(R.drawable.ic_tiktok, "TikTok") {
                        openLink("https://www.tiktok.com/")
                    }

                    SocialMediaIcon(R.drawable.ic_instagram, "Instagram") {
                        openLink("https://www.instagram.com/")
                    }

                    SocialMediaIcon(R.drawable.ic_facebook, "Facebook") {
                        openLink("https://www.facebook.com/")
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
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF7F7F7)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "$label Icon",
                modifier = Modifier.size(30.dp)
            )
        }

        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF444444)
        )
    }
}



@Composable
fun RadioButtonOptionAudio(option: String, selectedOption: MutableState<String>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { selectedOption.value = option }
            .padding(vertical = 6.dp)
    ) {
        RadioButton(
            selected = selectedOption.value == option,
            onClick = { selectedOption.value = option },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFFfb727c),
                unselectedColor = Color.LightGray
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = option,
            fontSize = 14.sp,
            color = if (selectedOption.value == option) Color.Black else Color.Gray
        )
    }
}


fun downloadFile(
    context: Context,
    url: String,
    title: String?,
    mimeType: String,
    viewModel: MainViewModel,
    apl: api?
) {
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val uri = Uri.parse(url)
    val fileName = "$title.${MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)}"
    val request = DownloadManager.Request(uri)
        .setMimeType(mimeType)
        .setTitle(title)
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

    val downloadId = downloadManager.enqueue(request)


    val video = Video(
        title = title ?: "Unknown Video",
        url = url,
        Image = apl?.thumbnail ?: "",
        path = fileName,
        downloadProgress = 0.0f
    )
    viewModel.Insert(video)

    observeDownloadProgress(context, downloadId, fileName, viewModel)
}


private fun observeDownloadProgress(
    context: Context,
    downloadId: Long,
    path: String,
    viewModel: MainViewModel
) {
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    Thread {
        var downloading = true
        while (downloading) {
            val query = DownloadManager.Query().setFilterById(downloadId)
            val cursor = downloadManager.query(query)

            if (cursor.moveToFirst()) {
                val bytesDownloaded =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                val bytesTotal =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

                if (bytesTotal > 0) {
                    val progress = bytesDownloaded.toFloat() / bytesTotal.toFloat()
                    Log.d("Download", "Progress: $progress")


                    viewModel.updateProgress(path, progress)
                }

                val status =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
                if (status == DownloadManager.STATUS_SUCCESSFUL || status == DownloadManager.STATUS_FAILED) {
                    downloading = false
                }
            }
            cursor.close()
            Thread.sleep(1000)
        }
    }.start()
}
