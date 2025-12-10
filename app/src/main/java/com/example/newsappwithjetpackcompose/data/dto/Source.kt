package com.example.newsappwithjetpackcompose.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String?,
    val name: String?
)