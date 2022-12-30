package com.example.composeapp.ui.view.page.photos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

const val PHOTO_HOME_PAGE = "Photos"

//Testing
const val PHOTO_PAGE_TEST_TAG = "photo_page_test_tag"

@Composable
fun PhotoHomePage() {
    Box(modifier = Modifier.fillMaxSize().testTag(PHOTO_PAGE_TEST_TAG)) {
        Text(text = PHOTO_HOME_PAGE, modifier = Modifier.align(Alignment.Center))
    }
}