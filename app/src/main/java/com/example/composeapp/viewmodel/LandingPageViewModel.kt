package com.example.composeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingPageViewModel @Inject constructor() : ViewModel() {

    val state: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    private val displayingTabs = MutableStateFlow(true)
    private val activeTab = MutableStateFlow(BottomTab.HOME)

    init {
        viewModelScope.launch {
            combine(
                displayingTabs,
                activeTab
            ) { displayingTabs, activeTab ->
                UiState(
                    activeTab, displayingTabs
                )
            }.collect {
                state.value = it
            }
        }
    }

    fun setActiveTab(tab : BottomTab){
        activeTab.value = tab
    }

    fun displayTabs(display : Boolean){
        displayingTabs.value = display
    }

    class UiState(
        val bottomTab: BottomTab = BottomTab.HOME,
        val displayingTabs: Boolean = true
    )

    enum class BottomTab {
        HOME,
        SHOPPING,
        ALERT,
        PROFILE
    }
}