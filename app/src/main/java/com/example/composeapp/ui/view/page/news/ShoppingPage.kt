package com.example.composeapp.ui.view.page.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

const val SHOPPING_PAGE = "ShoppingPage"

//Testing
const val SHOPPING_PAGE_TEST_TAG = "shopping_page_test_tag"

@Composable
fun ShoppingPage() {
    Box(modifier = Modifier.fillMaxSize().testTag(SHOPPING_PAGE_TEST_TAG)) {
        Text(text = SHOPPING_PAGE, modifier = Modifier.align(Alignment.Center))
    }
}