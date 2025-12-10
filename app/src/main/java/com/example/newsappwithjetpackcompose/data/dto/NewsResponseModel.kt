package com.example.newsappwithjetpackcompose.data.dto

data class NewsResponseModel(
    val articles: List<Article>?,
    val status: String?,
    val totalResults: Int?
)