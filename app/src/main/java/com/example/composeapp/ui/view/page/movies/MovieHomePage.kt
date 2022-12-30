package com.example.composeapp.ui.view.page.movies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

const val MOVIE_HOME_PAGE = "Movies"

const val MOVIE_PAGE_TEST_TAG = "movie_page_test_tag"

@Composable
fun MovieHomePage(){
    Box(modifier = Modifier.fillMaxSize().testTag(MOVIE_PAGE_TEST_TAG)) {
        Text(text = MOVIE_HOME_PAGE, modifier = Modifier.align(Alignment.Center))
    }
}