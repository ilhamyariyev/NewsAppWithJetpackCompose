package com.example.newsappwithjetpackcompose.domain.state

import com.example.newsappwithjetpackcompose.data.dto.Article

data class NewsUiState(
    val isLoading: Boolean = false,
    val news: List<Article>? = emptyList(),
    val error: String? = null
)