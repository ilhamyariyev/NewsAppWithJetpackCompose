package com.example.newsappwithjetpackcompose.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappwithjetpackcompose.data.dto.Article
import com.example.newsappwithjetpackcompose.domain.repository.NewsRepository
import com.example.newsappwithjetpackcompose.domain.state.NewsUiState
import com.example.newsappwithjetpackcompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _newsState = MutableStateFlow(NewsUiState())
    val newsState = _newsState.asStateFlow()

    private val _categoryState = MutableStateFlow(NewsUiState())
    val categoryState = _categoryState.asStateFlow()

    private val _searchState = MutableStateFlow(NewsUiState())
    val searchState = _searchState.asStateFlow()

    init {
        getTopHeadlines()
        getCategoryNews()
    }


    fun getTopHeadlines(sources: String = "bbc-news") {
        viewModelScope.launch {
            _newsState.value = NewsUiState(isLoading = true)
            val result = newsRepository.getTopHeadlines(sources)

            _newsState.value = when (result) {
                is Resource.Success -> {
                    NewsUiState(news = result.data.articles)
                }

                is Resource.Error -> NewsUiState(error = result.exception.message)
                else -> NewsUiState()
            }
        }
    }

    fun getCategoryNews(category: String = "business") {
        viewModelScope.launch {
            _categoryState.value = NewsUiState(isLoading = true)
            val result = newsRepository.getCategoryNews(category)

            _categoryState.value = when (result) {
                is Resource.Success -> {
                    NewsUiState(news = result.data.articles)
                }

                is Resource.Error -> NewsUiState(error = result.exception.message)
                else -> NewsUiState()
            }
        }
    }

    fun getSearchNews(query: String) {
        viewModelScope.launch {
            _searchState.value = NewsUiState(isLoading = true)
            val result = newsRepository.getSearchNews(query)

            _searchState.value = when (result) {
                is Resource.Success -> {
                    NewsUiState(news = result.data.articles)
                }

                is Resource.Error -> NewsUiState(error = result.exception.message)
                else -> NewsUiState()
            }
        }
    }


}