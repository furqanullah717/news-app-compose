package com.codewithfk.newsapp.presentation.news_details

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.codewithfk.newsapp.data.model.News
import com.codewithfk.newsapp.presentation.State


@Composable
fun NewsDetailsScreen(navController: NavController, news: News, isLocal: Boolean = false) {
    NewsDetails(navController = navController, news = news, isLocal = isLocal)
}

@Composable
fun NewsDetails(navController: NavController, news: News, isLocal: Boolean = false) {
    val viewModel: NewsDetailsViewModel = hiltViewModel()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.1f))
    ) {
        AsyncImage(
            model = news.image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.Crop
        )
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            val (backBtn, topSpace, summary, newContent) = createRefs()
            Spacer(modifier = Modifier
                .height(350.dp)
                .constrainAs(topSpace) {
                    top.linkTo(parent.top)
                })
            Image(imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
                    .constrainAs(backBtn) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    })
            Box(modifier = Modifier
                .constrainAs(newContent) {
                    top.linkTo(topSpace.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.wrapContent
                }
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(Color.White)
                .padding(vertical = 50.dp, horizontal = 16.dp)) {
                Text(text = news.text, fontSize = 14.sp, modifier = Modifier.fillMaxSize())
            }
            Column(modifier = Modifier
                .padding(vertical = 16.dp)
                .width(300.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
                .padding(16.dp)
                .constrainAs(summary) {
                    top.linkTo(topSpace.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(topSpace.bottom)
                }) {
                Text(text = news.publish_date, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = news.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = news.authors?.joinToString(", ") ?: "", fontSize = 10.sp)
            }
        }
        Image(
            imageVector = if (isLocal) Icons.Filled.Delete else Icons.Filled.Favorite,
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .clip(CircleShape)
                .background(Color.Red)
                .height(48.dp)
                .width(48.dp)
                .padding(8.dp)
                .align(Alignment.BottomEnd)
                .clickable {
                    if (isLocal)
                        viewModel.removeNews(news)
                    else
                        viewModel.addNews(news)
                }
        )

    }

    val context = LocalContext.current
    val state = viewModel.state.collectAsState()
    LaunchedEffect(state.value) {
        if (state.value is State.Loading) {
            return@LaunchedEffect
        }
        if (state.value is State.Success) {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            if ((state.value as State.Success<BookmarkAction>).data == BookmarkAction.REMOVE) {
                navController.popBackStack()
            }
        } else {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

}