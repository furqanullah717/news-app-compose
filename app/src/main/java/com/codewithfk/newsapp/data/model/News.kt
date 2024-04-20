package com.codewithfk.newsapp.data.model

data class News(
    val authors: List<String>?,
    val id: Int,
    val image: String,
    val language: String,
    val publish_date: String,
    val sentiment: Double,
    val source_country: String,
    val summary: String?,
    val text: String,
    val title: String,
    val url: String
)