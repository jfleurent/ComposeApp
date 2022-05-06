package com.example.composeapp.ui.view.page.photos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

const val PHOTO_HOME_PAGE = "PhotoHomePage"

@Composable
fun PhotoHomePage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = PHOTO_HOME_PAGE, modifier = Modifier.align(Alignment.Center))
    }
}