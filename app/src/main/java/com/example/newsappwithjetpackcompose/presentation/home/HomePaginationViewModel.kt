//package com.example.newsappwithjetpackcompose.presentation.home
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.newsappwithjetpackcompose.data.dto.Article
//import com.example.newsappwithjetpackcompose.data.dto.NewsResponseModel
//import com.example.newsappwithjetpackcompose.domain.repository.NewsRepository
//import com.example.newsappwithjetpackcompose.util.Resource
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//import kotlinx.coroutines.delay
//import kotlin.collections.plus
//import kotlin.let
//
//@HiltViewModel
//class HomePaginationViewModel @Inject constructor(
//    private val newsRepository: NewsRepository
//) : ViewModel() {
//
//    private val _news = MutableStateFlow<List<Article>?>(emptyList())
//    val news = _news.asStateFlow()
//
//    private val _isLoading = MutableStateFlow(false)
//    val isLoading = _isLoading.asStateFlow()
//
//    var page = 1
//        private set
//
//    fun initList(initial: List<Article>?) {
//        _news.value?.let {
//            if (it.isEmpty()) {
//                _news.value = initial ?: emptyList()
//            }
//        }
//    }
//
//    fun loadNextNewsPage() {
//        if (_isLoading.value) return
//
//        viewModelScope.launch {
//            _isLoading.value = true
//
//            val res = newsRepository.getTopHeadlines(page + 1)
//            handleResult(res)
//
//            _isLoading.value = false
//        }
//    }
//
//    private fun handleResult(res: Resource<NewsResponseModel>) {
//        when (res) {
//            is Resource.Success -> {
//                val newArticles = res.data.articles
//                newArticles?.let {
//                    page++
//                    _news.value = _news.value?.plus(it)
//                }
//            }
//            is Resource.Error -> {
//                // Burada istəsən error göstərə bilərsən
//            }
//
//            Resource.Loading -> {}
//        }
//    }
//}