package com.example.composeapp.datasource.repository

import android.util.Log
import com.example.composeapp.datasource.local.dao.ArticleDao
import com.example.composeapp.datasource.model.Article
import com.example.composeapp.datasource.remote.ArticleApi
import com.example.composeapp.util.toLocalDataTime
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime

class ArticleRepository(
    val articleDao: ArticleDao,
    val articleApi: ArticleApi
) : IArticleRepository {
    private val TAG: String = ArticleRepository::class.java.simpleName

    override suspend fun getAllArticlesFromApi(
        query: String,
        from: String,
        to: String,
        sortBy: String,
        pageSize: Int,
        page: Int
    ) {
        val list = articleDao.getArticlesWithTag("recommended_$query")
        val maxTime =
            if (list.isEmpty())
                LocalDateTime.now().minusDays(16)
            else list
                .map { article -> article.publishedAt?.toLocalDataTime() ?: LocalDateTime.now() }
                .reduce { maxTime, time -> if (maxTime > time) maxTime else time }

        if (maxTime.isBefore(LocalDateTime.now().minusDays(14))) {
            articleApi.getEverything(query, from, to, sortBy, pageSize, page).run {
                Log.d(TAG, "Collecting Articles")
                body()?.articles?.let { articles ->
                    insertArticles(articles.onEach { article ->
                        article.tag = "recommended_$query"
                    })
                } ?: Log.d(
                    TAG,
                    "Failed to get article list result:  ${errorBody()?.string()}"
                )
            }
        }
    }

    override suspend fun getLatestArticlesFromApi(
        country: String,
        category: String,
        pageSize: Int,
        page: Int
    ) {
        val list = articleDao.getArticlesWithTag(category)
        val maxTime =
            if (list.isEmpty())
                LocalDateTime.now().minusDays(1)
            else list
                .map { article -> article.publishedAt?.toLocalDataTime() ?: LocalDateTime.now() }
                .reduce { maxTime, time -> if (maxTime > time) maxTime else time }

        if (maxTime.isBefore(LocalDateTime.now().minusDays(1))) {
            articleApi.getTopHeadLines(country, category, pageSize, page).run {
                Log.d(TAG, "Collecting Articles")
                body()?.articles?.let { articles ->
                    insertArticles(articles.onEach { article ->
                        article.tag = category
                    })
                } ?: Log.d(
                    TAG,
                    "Failed to get article list result:  ${errorBody()?.string()}"
                )
            }

        }
    }

    override fun getAllArticles(): Flow<List<Article>> {
        return articleDao.getArticlesFlow()
    }

    override fun getFavoriteArticles(): Flow<List<Article>> {
        return articleDao.getFavoriteArticlesFlow()
    }

    override suspend fun getArticlesWithTag(tag: String): Flow<List<Article>>{
        return articleDao.getArticlesWithTagFlow(tag)
    }

    override suspend fun insertArticles(articles: List<Article>) {
        articleDao.insertArticles(articles)
    }

    override suspend fun insertArticle(article: Article) {
        articleDao.insertArticle(article)
    }

    override suspend fun getArticleFromId(id: Long): Flow<Article> {
        return articleDao.getArticleWithIdFlow(id)
    }

    override suspend fun updateArticle(article: Article){
        articleDao.updateArticle(article)
    }

    override fun searchArticles(searchTerm: String, tag: String): List<Article> {
        return articleDao.getLocalSearchFlow(searchTerm, tag)
    }
}