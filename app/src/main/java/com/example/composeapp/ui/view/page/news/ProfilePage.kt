package com.example.composeapp.ui.view.page.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

const val PROFILE_PAGE = "ProfilePage"
@Composable
fun ProfilePage(){
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = PROFILE_PAGE, modifier = Modifier.align(Alignment.Center))
    }
}