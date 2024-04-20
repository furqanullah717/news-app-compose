package com.codewithfk.newsapp.utils

import com.codewithfk.newsapp.data.model.News
import com.google.gson.Gson
import java.net.URLDecoder
import java.net.URLEncoder

object NavRoute {
    fun createNewsDetailsRoute(news: News): String {
        val encodedImage = URLEncoder.encode(news.image, "utf-8")
        val encodedUrl = URLEncoder.encode(news.url, "utf-8")
        val tempNews = news.copy(image = encodedImage, url = encodedUrl)
        val gson = Gson().toJson(tempNews)
        return "/details/news=$gson"
    }

    fun getNewsFromRoute(json: String): News {
        val news = Gson().fromJson(json, News::class.java)
        val decodedImage = URLDecoder.decode(news.image, "utf-8")
        val decodeUrl = URLDecoder.decode(news.url, "utf-8")
        return news.copy(image = decodedImage, url = decodeUrl)
    }
}