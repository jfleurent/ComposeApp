package com.example.composeapp.datasource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.composeapp.datasource.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ArticleDao {

    @Insert(onConflict = REPLACE) 
    abstract suspend fun insertArticle(article: Article)

    @Insert(onConflict = REPLACE)
    abstract suspend fun insertArticles(articles : List<Article>)

    @Query("SELECT * FROM Article")
    abstract fun getArticlesFlow() : Flow<List<Article>>

    @Query("SELECT * FROM Article WHERE primaryKey = :primaryKey")
    abstract fun getArticleWithIdFlow(primaryKey : Long) : Flow<Article>

    @Update
    abstract fun updateArticle(article: Article)

    @Query("SELECT * FROM Article WHERE tag = :tag")
    abstract fun getArticlesWithTagFlow(tag : String) : Flow<List<Article>>

    @Query("SELECT * FROM Article WHERE favorite = 1")
    abstract fun getFavoriteArticlesFlow() : Flow<List<Article>>

    @Query("SELECT * FROM Article")
    abstract suspend fun getArticles() :List<Article>

    @Query("SELECT * FROM Article WHERE tag = :tag")
    abstract suspend fun getArticlesWithTag(tag : String) : List<Article>

    @Query("SELECT * FROM Article WHERE favorite = 1")
    abstract suspend fun getFavoriteArticles() : List<Article>
}