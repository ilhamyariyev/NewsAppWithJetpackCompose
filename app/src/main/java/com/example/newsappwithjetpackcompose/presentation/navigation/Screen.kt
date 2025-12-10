package com.example.newsappwithjetpackcompose.presentation.navigation

import com.example.newsappwithjetpackcompose.data.dto.Article
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {

    @Serializable
    object Home : Screen()
    @Serializable
    object Favorite : Screen()
    @Serializable
    data class Detail(val article: Article) : Screen()
}