package com.example.allvideodownload.presentation.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.allvideodownload.R
import com.example.allvideodownload.presentation.ui.screen.download.DownloadScreen
import com.example.allvideodownload.presentation.ui.screen.home.HomeScreen
import com.example.allvideodownload.presentation.ui.screen.progress.ProgressScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) { HomeScreen() }
        composable(Screen.ProgressScreen.route) { ProgressScreen() }
        composable(Screen.DownloadScreen.route) { DownloadScreen() }

    }

}

sealed class Screen(val route: String, val title: String, val icon: Int) {
    object HomeScreen : Screen("HomeScreen", "Home", icon = R.drawable.ic_home)
    object ProgressScreen : Screen("ProgressScreen", "Progress", icon = R.drawable.ic_download)
    object DownloadScreen : Screen("DownloadScreen", "Download", icon = R.drawable.ic_file_download)


}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf(
        Screen.HomeScreen,
        Screen.ProgressScreen,
        Screen.DownloadScreen
    )

    NavigationBar {
        val navStack by navController.currentBackStackEntryAsState()
        val current = navStack?.destination?.route

        items.forEach {
            val isSelected = current == it.route
            val contentColor = if (isSelected) Color(0XFFff8379) else Color.Gray

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Image(
                        painter = painterResource(id = it.icon),
                        contentDescription = "",
                        modifier = Modifier.size(25.dp),
                        colorFilter = ColorFilter.tint(contentColor)
                    )
                },
                label = {
                    if (isSelected) {
                        Text(text = it.title, color = contentColor)
                    }
                }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color.White)
            )
        }
    }


}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavEntry() {
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        BottomNavigation(navController = navController)
    }) {
        Navigation(navController = navController)
    }

}