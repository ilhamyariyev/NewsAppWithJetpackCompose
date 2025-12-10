package com.example.newsappwithjetpackcompose.domain.repository

import com.example.newsappwithjetpackcompose.data.api.NewsApi
import com.example.newsappwithjetpackcompose.data.dto.NewsResponseModel
import com.example.newsappwithjetpackcompose.util.Resource
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val api: NewsApi,
    //private val dao: FavoriteDao
) {
    suspend fun <T> safeCall(block: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(block())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getTopHeadlines(sources: String = "bbc-news"): Resource<NewsResponseModel> =
        safeCall { api.getTopHeadlines(sources) }

    suspend fun getCategoryNews(category: String): Resource<NewsResponseModel> =
        safeCall { api.getCategoryNews(category) }

    suspend fun getSearchNews(query: String): Resource<NewsResponseModel> =
        safeCall { api.getSearchNews(query) }

//    val allFavorites: Flow<List<Favorite>> = dao.getAllFavoriteItemsFlow()
//
//    suspend fun insert(favorite: Favorite) = dao.insert(favorite)
//
//    suspend fun delete(favorite: Favorite) = dao.delete(favorite)

}