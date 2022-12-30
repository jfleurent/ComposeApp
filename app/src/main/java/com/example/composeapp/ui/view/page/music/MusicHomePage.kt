package com.example.composeapp.ui.view.page.music

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

const val MUSIC_HOME_PAGE = "Music"

//Testing
const val MUSIC_PAGE_TEST_TAG = "music_page_test_tag"

@Composable
fun MusicHomePage() {
    Box(modifier = Modifier.fillMaxSize().testTag(MUSIC_PAGE_TEST_TAG)) {
        Text(text = MUSIC_HOME_PAGE, modifier = Modifier.align(Alignment.Center))
    }
}