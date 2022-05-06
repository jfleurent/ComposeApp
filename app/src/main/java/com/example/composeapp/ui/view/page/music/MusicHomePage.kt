package com.example.composeapp.ui.view.page.music

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

const val MUSIC_HOME_PAGE = "MusicHomePage"

@Composable
fun MusicHomePage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = MUSIC_HOME_PAGE, modifier = Modifier.align(Alignment.Center))
    }
}