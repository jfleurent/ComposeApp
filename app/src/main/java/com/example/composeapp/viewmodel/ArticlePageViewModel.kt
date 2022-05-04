package com.example.composeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.datasource.model.Article
import com.example.composeapp.datasource.repository.IArticleRepository
import com.example.composeapp.ui.view.testArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArticlePageViewModel @Inject constructor(private val repository: IArticleRepository) :
    ViewModel() {

    val state: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    private val activeArticle: MutableStateFlow<Article> = MutableStateFlow(Article(-1))

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                combine(activeArticle) {
                    UiState(it[0])
                }.collect {
                    state.value = it
                }
            }
        }
    }

    fun getArticle(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getArticleFromId(id).collect {
                    activeArticle.value = it
                }
            }
        }
    }

    fun favoriteArticle(article: Article) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateArticle(article.copy().apply { favorite = !favorite })
            }
        }
    }

    data class UiState(
        var article: Article = Article(-1)
    )
}