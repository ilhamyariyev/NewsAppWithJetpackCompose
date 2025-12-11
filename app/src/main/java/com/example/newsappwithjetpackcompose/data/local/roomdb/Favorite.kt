package com.info.androidileriders2.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_news")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val author: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    val description: String?,
    val sourceName: String?
)
