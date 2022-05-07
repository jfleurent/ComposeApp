package com.example.composeapp.viewmodel

import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeapp.ui.view.components.DrawerItem
import com.example.composeapp.ui.view.page.movies.MOVIE_HOME_PAGE
import com.example.composeapp.ui.view.page.music.MUSIC_HOME_PAGE
import com.example.composeapp.ui.view.page.news.ALERT_PAGE
import com.example.composeapp.ui.view.page.news.NEWS_HOME_PAGE
import com.example.composeapp.ui.view.page.news.PROFILE_PAGE
import com.example.composeapp.ui.view.page.news.SHOPPING_PAGE
import com.example.composeapp.ui.view.page.photos.PHOTO_HOME_PAGE
import com.example.composeapp.ui.view.page.settings.SETTINGS_PAGE
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
    private val searchFieldState = MutableStateFlow(SearchFieldState())
    private val currentPageState = MutableStateFlow(TabbedPageState())

    @OptIn(ExperimentalMaterial3Api::class)
    private val drawerState = MutableStateFlow(MenuDrawerState())

    private val uiStateStack: Stack<Pair<String, UiState>> = Stack()

    private var updateStateJob: Job? = null

    init {
        updateStateJob = viewModelScope.launch {
            withContext(
                Dispatchers.Main
            ) {
                combineStates(
                    appbarState,
                    searchFieldState,
                    currentPageState,
                    drawerState
                )
            }
        }
    }

    private suspend fun combineStates(
        appbarState: MutableStateFlow<AppBarState>,
        searchFieldState: MutableStateFlow<SearchFieldState>,
        currentPageState: MutableStateFlow<TabbedPageState>,
        drawerState: MutableStateFlow<MenuDrawerState>
    ) {
        combine(
            appbarState,
            searchFieldState,
            drawerState,
            currentPageState
        ) { appBar, search, drawer, currentPage ->
            UiState(
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


    fun pushCurrentState(destination: String) {
        if (uiStateStack.isEmpty() || (uiStateStack.peek().first != destination
                    && state.value.currentPageState.currentTab.name != destination)
        ) {
            uiStateStack.push(Pair(destination, state.value.copy()))
        }
    }

    fun onBack() {
        updateStateJob?.cancel()
        updateStateJob = viewModelScope.launch {
            withContext(Dispatchers.Main) {
                if (!uiStateStack.isEmpty()) {
                    val lastState = uiStateStack.pop().second
                    setCurrentPageState(lastState.currentPageState.currentTab,lastState.currentPageState.page)
                    setAppBarState(
                        lastState.appbarState.topBarVisible,
                        lastState.appbarState.bottomVarVisible
                    )
                    setSearchText(lastState.searchFieldState.searchText)
                    setSearchViewVisible(lastState.searchFieldState.searchViewVisible)
                    setSelectDrawerItem(lastState.drawerState.selectedLabel)
                    combineStates(
                        appbarState,
                        searchFieldState,
                        currentPageState,
                        drawerState
                    )
                }
            }
        }
    }

    private fun  setCurrentPageState(tab: BottomTab,page: TopLevelPage){
        val state = TabbedPageState(page,tab)
        currentPageState.value = state
    }

    fun setActiveTab(tab: BottomTab) {
        if(state.value.currentPageState.page.bottomTabs.contains(tab)){
            val state = TabbedPageState(state.value.currentPageState.page, tab)
            currentPageState.value = state
        }
    }

    fun setCurrentPage(page: TopLevelPage) {
        val state = TabbedPageState(page, state.value.currentPageState.currentTab)
        currentPageState.value = state
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
        val state = SearchFieldState(
            searchText = searchFieldState.value.searchText,
            searchViewVisible = visible
        )
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
        val appbarState: AppBarState = AppBarState(),
        val searchFieldState: SearchFieldState = SearchFieldState(),
        val drawerState: MenuDrawerState = MenuDrawerState(),
        val currentPageState: TabbedPageState = TabbedPageState()
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
        NEWS_HOME(destination = NEWS_HOME_PAGE),
        NEWS_SHOPPING(destination = SHOPPING_PAGE),
        NEWS_ALERT(destination = ALERT_PAGE),
        NEWS_PROFILE(destination = PROFILE_PAGE)
    }

    enum class TopLevelPage(
        val label: String,
        val bottomTabs: List<BottomTab> = emptyList()
    ) {
        NEWS(
            label = NEWS_HOME_PAGE,
            bottomTabs = listOf(
                BottomTab.NEWS_HOME,
                BottomTab.NEWS_SHOPPING,
                BottomTab.NEWS_ALERT,
                BottomTab.NEWS_PROFILE
            )
        ),
        MOVIE(label = MOVIE_HOME_PAGE),
        PHOTOS(label = PHOTO_HOME_PAGE),
        MUSIC(label = MUSIC_HOME_PAGE),
        SETTINGS(label = SETTINGS_PAGE);
    }

    data class TabbedPageState(val page: TopLevelPage = TopLevelPage.NEWS, val currentTab: BottomTab = BottomTab.NEWS_HOME)

    data class MenuDrawerState(
        var visibleState: DrawerState = DrawerState(DrawerValue.Closed),
        var selectedLabel: String = "News"
    )
}