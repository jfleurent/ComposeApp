package com.example.composeapp.ui.view.page.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

const val SETTINGS_PAGE = "Settings"

@Composable
fun SettingsPage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = SETTINGS_PAGE, modifier = Modifier.align(Alignment.Center))
    }
}