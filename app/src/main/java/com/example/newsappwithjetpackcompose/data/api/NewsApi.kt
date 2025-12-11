package com.example.newsappwithjetpackcompose.data.api

import com.example.newsappwithjetpackcompose.data.dto.NewsResponseModel
import com.example.newsappwithjetpackcompose.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("sources") sources: String = "bbc-news", @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponseModel

    @GET("top-headlines")
    suspend fun getCategoryNews(
        @Query("category") category: String, @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponseModel

    @GET("everything")
    suspend fun getSearchNews(
        @Query("q") query: String, @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponseModel
}