package com.example.composeapp.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.composeapp.datasource.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertArticle(article: Article)

    @Insert(onConflict = REPLACE)
    suspend fun insertArticles(articles: List<Article>)

    @Query("SELECT * FROM Article")
    fun getArticlesFlow(): Flow<List<Article>>

    @Query("SELECT * FROM Article WHERE primaryKey = :primaryKey")
    fun getArticleWithIdFlow(primaryKey: Long): Flow<Article>

    @Update
    fun updateArticle(article: Article)

    @Query("SELECT * FROM Article WHERE tag = :tag")
    fun getArticlesWithTagFlow(tag: String): Flow<List<Article>>

    @Query("SELECT * FROM Article WHERE favorite = 1")
    fun getFavoriteArticlesFlow(): Flow<List<Article>>

    @Query("SELECT * FROM Article")
    suspend fun getArticles(): List<Article>

    @Query("SELECT * FROM Article WHERE tag = :tag")
    suspend fun getArticlesWithTag(tag: String): List<Article>

    @Query("SELECT * FROM Article WHERE favorite = 1")
    suspend fun getFavoriteArticles(): List<Article>

    @Query("SELECT * " +
            "FROM Article " +
            "WHERE tag = :tag " +
            "AND (title LIKE :searchTerm " +
            "OR content LIKE :searchTerm " +
            "OR author LIKE :searchTerm) ")
    suspend fun getLocalSearchFlow(searchTerm: String, tag: String): List<Article>
}