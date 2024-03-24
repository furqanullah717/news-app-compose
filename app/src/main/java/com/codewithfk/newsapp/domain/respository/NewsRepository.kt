package com.codewithfk.newsapp.domain.respository

import com.codewithfk.newsapp.data.response.NewsResponse
import retrofit2.Response

interface NewsRepository {
    suspend fun getNews(
        language: String,
        text: String?,
        country: String?,
    ): Response<NewsResponse>
}