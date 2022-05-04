package com.example.composeapp.di

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.composeapp.datasource.local.AppDatabase
import com.example.composeapp.datasource.local.dao.ArticleDao
import com.example.composeapp.datasource.remote.ArticleApi
import com.example.composeapp.datasource.repository.ArticleRepository
import com.example.composeapp.datasource.repository.IArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase{
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "compose_app_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(database: AppDatabase): ArticleDao{
        return database.articleDao()
    }

    @Provides
    @Singleton
    fun provideBaseUrl() : String {
        return "https://newsapi.org/v2/"
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl : String) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideArticleApi(retrofit: Retrofit) : ArticleApi{
        return retrofit.create(ArticleApi::class.java);
    }

    @Provides
    @Singleton
    fun provideArticleRepository(articleApi: ArticleApi, articleDao: ArticleDao) : IArticleRepository {
        return ArticleRepository(articleDao,articleApi)
    }
}