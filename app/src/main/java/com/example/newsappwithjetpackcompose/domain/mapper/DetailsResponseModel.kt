package com.example.movawithjetpackcompose.domain.model

import com.example.newsappwithjetpackcompose.data.dto.Article
import com.example.newsappwithjetpackcompose.data.dto.Source
import com.info.androidileriders2.roomDB.Favorite

fun Article.toFavorite() = Favorite(
    id = 0,
    title = this.title,
    author = this.author,
    url = this.url ?: "",
    urlToImage = this.urlToImage,
    publishedAt = this.publishedAt,
    content = this.content,
    description = this.description,
    sourceName = this.source?.name
)

fun Favorite.toArticle(): Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = Source(id = null, name = this.sourceName),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}