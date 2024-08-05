package com.codewithfk.newsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.codewithfk.newsapp.presentation.bookmarks.BookmarkScreen
import com.codewithfk.newsapp.presentation.home.HomeScreen
import com.codewithfk.newsapp.presentation.news_details.NewsDetailsScreen
import com.codewithfk.newsapp.ui.theme.NewsAppTheme
import com.codewithfk.newsapp.utils.NavRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                val navController = rememberNavController()
                val isBottomBarVisible = remember {
                    mutableStateOf(true)
                }
                Scaffold(
                    bottomBar = {
                        AnimatedVisibility(visible = isBottomBarVisible.value) {
                            BottomAppBar(containerColor = Color.Blue.copy(alpha = 0.3f)) {
                                val currentRoute =
                                    navController.currentBackStackEntryAsState().value?.destination?.route
                                bottomNavItems.forEach {
                                    NavigationBarItem(
                                        icon = {
                                            Image(
                                                imageVector = it.icon,
                                                contentDescription = null
                                            )
                                        },
                                        label = { Text(text = it.title) },
                                        selected = currentRoute == it.route,
                                        onClick = {
                                            navController.navigate(it.route) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = NavScreen.Home.route
                        ) {
                            composable(NavScreen.Home.route) {
                                HomeScreen(navController = navController)
                                isBottomBarVisible.value = true
                            }
                            composable("/details/news={news}&isLocal={isLocal}") {
                                val newsJson = it.arguments?.getString("news")
                                val isLocal = it.arguments?.getString("isLocal").toBoolean()
                                val news = NavRoute.getNewsFromRoute(newsJson!!)
                                NewsDetailsScreen(
                                    navController = navController,
                                    news,
                                    isLocal ?: false
                                )
                                isBottomBarVisible.value = false
                            }
                            composable(NavScreen.Bookmarks.route) {
                                BookmarkScreen(navController)
                                isBottomBarVisible.value = true
                            }
                        }
                    }
                }
            }
        }
    }
}

sealed class NavScreen(val route: String, val icon: ImageVector, val title: String) {
    object Home : NavScreen("/home", Icons.Filled.Home, "Home")
    object Bookmarks : NavScreen("/bookmarks", Icons.Filled.Favorite, "Bookmarks")
}

val bottomNavItems = listOf(
    NavScreen.Home,
    NavScreen.Bookmarks
)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsAppTheme {
    }
}