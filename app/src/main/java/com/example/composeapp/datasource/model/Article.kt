package com.example.composeapp.datasource.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Article(
    @PrimaryKey(autoGenerate = true) val primaryKey: Long,
    val title: String? = null,
    val author: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null,
    var favorite: Boolean = false,
    var tag : String? = ""
)