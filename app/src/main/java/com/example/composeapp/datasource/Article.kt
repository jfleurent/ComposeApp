package com.example.composeapp.datasource

import java.time.LocalDateTime

data class Article(
    val title: String,
    val author: String,
    val timeCreated: LocalDateTime? = null,
    val contents: String? = null
)