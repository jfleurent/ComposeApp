package com.example.composeapp.ui.view.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


const val ALERT_PAGE = "AlertPage"

@Composable
fun AlertPage(){
    Box(modifier = Modifier.fillMaxSize()){
        Text(text = ALERT_PAGE, modifier = Modifier.align(Alignment.Center))
    }
}