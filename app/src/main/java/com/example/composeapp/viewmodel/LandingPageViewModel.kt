package com.example.composeapp.viewmodel

import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.ui.view.components.DrawerItem
import com.example.composeapp.ui.view.page.news.ALERT_PAGE
import com.example.composeapp.ui.view.page.news.NEWS_HOME_PAGE
import com.example.composeapp.ui.view.page.news.PROFILE_PAGE
import com.example.composeapp.ui.view.page.news.SHOPPING_PAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class LandingPageViewModel @Inject constructor() : ViewModel() {

    val TAG = LandingPageViewModel::class.java.simpleName

    val state: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    private val appbarState = MutableStateFlow(AppBarState())
    private val tabbedPageState = MutableStateFlow(TabbedPageState())
    private val searchFieldState = MutableStateFlow(SearchFieldState())
    private val currentPageState = MutableStateFlow(TopLevelPage.NEWS)

    @OptIn(ExperimentalMaterial3Api::class)
    private val drawerState = MutableStateFlow(MenuDrawerState())

    private val uiStateStack: Stack<UiState> = Stack()

    private var updateStateJob : Job? = null

    init {
        updateStateJob = viewModelScope.launch {
            withContext(
                Dispatchers.Main
            ) {
                combineStates(appbarState,tabbedPageState,searchFieldState,currentPageState,drawerState)
            }
        }
    }

    private suspend fun combineStates(
        appbarState: MutableStateFlow<AppBarState>,
        tabbedPageState: MutableStateFlow<TabbedPageState>,
        searchFieldState: MutableStateFlow<SearchFieldState>,
        currentPageState: MutableStateFlow<TopLevelPage>,
        drawerState: MutableStateFlow<MenuDrawerState>
    ) {
        combine(
            appbarState,
            tabbedPageState,
            searchFieldState,
            drawerState,
            currentPageState
        ) { appBar, tabPage, search, drawer, currentPage ->
            UiState(
                tabbedPageState = tabPage,
                appbarState = appBar,
                searchFieldState = search,
                drawerState = drawer,
                currentPageState = currentPage
            )
        }.cancellable()
            .collect {
                state.value = it
            }
    }

    fun pushCurrentState() {
            uiStateStack.push(state.value.copy())
    }

    fun onBack() {
        updateStateJob?.cancel()
        updateStateJob = viewModelScope.launch {
            withContext(Dispatchers.Main) {
                if(!uiStateStack.isEmpty()){
                    val lastState = uiStateStack.pop()
                    setActiveTab(lastState.tabbedPageState.newsPageTabState)
                    setCurrentPage(lastState.currentPageState)
                    setAppBarState(lastState.appbarState.topBarVisible,lastState.appbarState.bottomVarVisible)
                    setSearchText(lastState.searchFieldState.searchText)
                    setSearchViewVisible(lastState.searchFieldState.searchViewVisible)
                    setSelectDrawerItem(lastState.drawerState.selectedLabel)
                    combineStates(appbarState,tabbedPageState,searchFieldState,currentPageState,drawerState)
                }
            }
        }
    }

    fun setActiveTab(tab: BottomTab) {
        val state = TabbedPageState()
        when (currentPageState.value) {
            TopLevelPage.NEWS -> state.newsPageTabState = tab
            else -> {}
        }
        tabbedPageState.value = state
    }

    fun setCurrentPage(page: TopLevelPage) {
        currentPageState.value = page
    }

    fun setAppBarState(displayingTopBar: Boolean, displayingBottomBar: Boolean) {
        appbarState.value = AppBarState(displayingTopBar, displayingBottomBar)
    }

    fun setSearchText(text: String) {
        val state = SearchFieldState(
            searchText = text,
            searchViewVisible = searchFieldState.value.searchViewVisible
        )
        searchFieldState.value = state
    }

    fun setSearchViewVisible(visible: Boolean) {
        val state = SearchFieldState(searchViewVisible = visible)
        if (!visible)
            state.searchText = ""
        searchFieldState.value = state
    }

    fun setDrawerOpenState() {
        val state = MenuDrawerState(selectedLabel = drawerState.value.selectedLabel)
        when (drawerState.value.visibleState.currentValue) {
            DrawerValue.Closed -> state.visibleState = DrawerState(DrawerValue.Open)
            DrawerValue.Open -> state.visibleState = DrawerState(DrawerValue.Closed)
        }
        drawerState.value = state
    }

    fun setSelectDrawerItem(label: String) {
        val state = MenuDrawerState()
        state.selectedLabel = label
        drawerState.value = state
    }

    data class UiState @OptIn(ExperimentalMaterial3Api::class) constructor(
        val tabbedPageState: TabbedPageState = TabbedPageState(),
        val appbarState: AppBarState = AppBarState(),
        val searchFieldState: SearchFieldState = SearchFieldState(),
        val drawerState: MenuDrawerState = MenuDrawerState(),
        val currentPageState: TopLevelPage = TopLevelPage.NEWS
    )

    data class AppBarState(
        val topBarVisible: Boolean = true,
        val bottomVarVisible: Boolean = true,
    )

    data class SearchFieldState(
        var searchText: String = "",
        var searchViewVisible: Boolean = false
    )

    enum class BottomTab(val destination: String) {
        NEWS_HOME(NEWS_HOME_PAGE),
        NEWS_SHOPPING(SHOPPING_PAGE),
        NEWS_ALERT(ALERT_PAGE),
        NEWS_PROFILE(PROFILE_PAGE)
    }

    enum class TopLevelPage(val label: String) {
        NEWS("News"),
        MOVIE("Movies"),
        PHOTOS("Photos"),
        MUSIC("Music"),
        SETTINGS("Settings")
    }

    data class TabbedPageState(var newsPageTabState: BottomTab = BottomTab.NEWS_HOME)

    data class MenuDrawerState(
        var visibleState: DrawerState = DrawerState(DrawerValue.Closed),
        var selectedLabel: String = "News"
    )
}