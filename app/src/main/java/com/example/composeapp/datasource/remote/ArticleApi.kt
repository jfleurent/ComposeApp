package com.example.composeapp.datasource.remote

import com.example.composeapp.API_KEY
import com.example.composeapp.datasource.model.ArticleResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ArticleApi {

    @Headers("X-Api-Key: $API_KEY")
    @GET("everything")
    suspend fun getEverything(
        @Query("q") term: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("sortBy") sortBy: String,
        @Query("pageSize") pageSize : Int,
        @Query("page") page : Int
        ): Response<ArticleResult>

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadLines(
        @Query("country") country: String,
        @Query("category") category : String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page : Int
    ): Response<ArticleResult>
}


const val RELEVANCY = "relevancy"
const val POPULARITY = "popularity"
const val PUBLISH_AT = "publishAt"
