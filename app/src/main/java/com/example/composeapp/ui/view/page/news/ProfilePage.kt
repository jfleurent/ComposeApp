package com.example.composeapp.ui.view.page.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

const val PROFILE_PAGE = "ProfilePage"

//Testing
const val PROFILE_PAGE_TEST_TAG = "profile_page_test_tag"

@Composable
fun ProfilePage(){
    Box(modifier = Modifier.fillMaxSize().testTag(PROFILE_PAGE_TEST_TAG)){
        Text(text = PROFILE_PAGE, modifier = Modifier.align(Alignment.Center))
    }
}