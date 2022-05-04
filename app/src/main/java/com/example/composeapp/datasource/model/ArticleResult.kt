package com.example.composeapp.datasource.model

data class ArticleResult(
    val status: Boolean?,
    val totalResults: Int?,
    val articles: List<Article>?
)