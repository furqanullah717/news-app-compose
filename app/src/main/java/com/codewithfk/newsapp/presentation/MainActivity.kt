package com.codewithfk.newsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "/home") {
                        composable("/home") {
                            HomeScreen(navController = navController)
                        }
                        composable("/details/news={news}") {
                            val newsJson = it.arguments?.getString("news")
                            val news = NavRoute.getNewsFromRoute(newsJson!!)
                            NewsDetailsScreen(navController = navController,news)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsAppTheme {
    }
}