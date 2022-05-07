package com.example.composeapp.datasource.repository

import com.example.composeapp.datasource.model.Article
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query


interface IArticleRepository {

    suspend fun getAllArticlesFromApi(
        query: String,
        from: String,
        to: String,
        sortBy: String,
        pageSize: Int,
        page: Int
    )

    suspend fun getLatestArticlesFromApi(
        country: String,
        category: String,
        pageSize: Int,
        page: Int
    )

    fun getAllArticles(): Flow<List<Article>>
    fun getFavoriteArticles(): Flow<List<Article>>
    suspend fun getArticlesWithTag(tag: String): Flow<List<Article>>
    suspend fun insertArticles(articles: List<Article>)
    suspend fun insertArticle(article: Article)
    suspend fun getArticleFromId(id: Long): Flow<Article>
    suspend fun updateArticle(article: Article)
    suspend fun searchArticles(searchTerm : String, tag: String) : List<Article>
}