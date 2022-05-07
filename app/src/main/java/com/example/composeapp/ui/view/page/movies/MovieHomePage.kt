package com.example.composeapp.ui.view.page.movies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

const val MOVIE_HOME_PAGE = "Movies"

@Composable
fun MovieHomePage(){
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = MOVIE_HOME_PAGE, modifier = Modifier.align(Alignment.Center))
    }
}