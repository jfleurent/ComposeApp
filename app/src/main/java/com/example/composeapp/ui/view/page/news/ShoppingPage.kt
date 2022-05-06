package com.example.composeapp.ui.view.page.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

const val SHOPPING_PAGE = "ShoppingPage"

@Composable
fun ShoppingPage() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = SHOPPING_PAGE, modifier = Modifier.align(Alignment.Center))
    }
}