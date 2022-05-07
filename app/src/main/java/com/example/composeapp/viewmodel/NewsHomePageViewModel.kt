package com.example.composeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.datasource.model.Article
import com.example.composeapp.datasource.remote.PUBLISH_AT
import com.example.composeapp.datasource.repository.IArticleRepository
import com.example.composeapp.util.toISO8601String
import com.example.composeapp.util.toLocalDataTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class NewsHomePageViewModel @Inject constructor(private val repository: IArticleRepository) :
    ViewModel() {

    private val TAG = NewsHomePageViewModel::class.simpleName

    val state: MutableStateFlow<UiState> = MutableStateFlow(UiState())

    private val activeTab: MutableStateFlow<TabPage> = MutableStateFlow(TabPage.LATEST)

    private val latestFeed: MutableStateFlow<List<Article>> = MutableStateFlow(emptyList())

    private val recommendedFeed: MutableStateFlow<List<Article>> = MutableStateFlow(emptyList())

    var recommendedFeedJob: Job? = null
    var latestFeedJob: Job? = null

    init {
        setActiveTab(TabPage.LATEST)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                combine(
                    latestFeed,
                    recommendedFeed,
                    activeTab
                ) { latestFeed, recommendedFeed, activeTab ->
                    UiState(
                        latestFeed,
                        recommendedFeed,
                        activeTab
                    )
                }.collect {
                    state.value = it
                }
            }
        }
    }

    fun searchArticles(searchTerm: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (searchTerm.isEmpty()) {
                    setActiveTab(state.value.activeTab)
                } else {
                    latestFeed.value =
                        repository.searchArticles("%$searchTerm%", state.value.activeTab.label)
                    recommendedFeed.value = repository.searchArticles(
                        "%$searchTerm%",
                        "recommended_${state.value.activeTab.label}"
                    )
                }
            }
        }
    }

    private fun getRecommendedArticles(
        query: String,
        from: String = toISO8601String(LocalDateTime.now()),
        to: String = toISO8601String(from.toLocalDataTime().minusDays(30)),
        sortBy: String = PUBLISH_AT,
        pageSize: Int = 30,
        page: Int = 1
    ) {
        recommendedFeedJob?.cancel()
        recommendedFeedJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getAllArticlesFromApi(query, from, to, sortBy, pageSize, page)
                repository.getArticlesWithTag("recommended_$query")
                    .cancellable()
                    .collect {
                        recommendedFeed.value =
                            if (it.isNotEmpty())
                                it.sortedBy { article -> article.publishedAt }
                                    .reversed()
                                    .subList(0, 10)
                            else
                                it
                    }
            }
        }
    }

    private fun getLatestArticles(
        category: String,
        country: String = "us",
        pageSize: Int = 20,
        page: Int = 1
    ) {
        latestFeedJob?.cancel()
        latestFeedJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getLatestArticlesFromApi(country, category, pageSize, page)
                repository.getArticlesWithTag(category)
                    .cancellable()
                    .collect {
                        latestFeed.value =
                            if (it.isNotEmpty())
                                it.sortedBy { article -> article.publishedAt }
                                    .reversed()
                                    .subList(0, 10)
                            else
                                it
                    }
            }
        }
    }

    fun setActiveTab(tabPage: TabPage) {
        getRecommendedArticles(tabPage.label)
        getLatestArticles(tabPage.label)
        activeTab.value = tabPage
    }

    fun favoriteArticle(article: Article) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateArticle(article.copy().apply { favorite = !favorite })
            }
        }
    }

    enum class TabPage(val label: String) {
        LATEST("Latest"),
        DECORATIVE("Decorative"),
        MUSIC("Music"),
        STYLE("Style"),
        TECHNOLOGY("Technology"),
        BUSINESS("Business")
    }

    data class UiState(
        var latestFeed: List<Article> = emptyList(),
        var recommendedFeed: List<Article> = emptyList(),
        var activeTab: TabPage = TabPage.LATEST
    )

}


