package com.codewithfk.newsapp.data.repository

import com.codewithfk.newsapp.data.response.NewsResponse
import com.codewithfk.newsapp.data.web.NewApi
import com.codewithfk.newsapp.domain.respository.NewsRepository
import retrofit2.Response
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(val api: NewApi) : NewsRepository {
    override suspend fun getNews(
        language: String,
        text: String?,
        country: String?
    ): Response<NewsResponse> {
        return api.getNews(country, language, text)
    }
}