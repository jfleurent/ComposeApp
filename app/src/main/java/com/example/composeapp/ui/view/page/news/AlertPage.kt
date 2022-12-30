package com.example.composeapp.ui.view.page.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag


const val ALERT_PAGE = "AlertPage"

//Testing
const val ALERT_PAGE_TEST_TAG = "alert_page_test_tag"

@Composable
fun AlertPage(){
    Box(modifier = Modifier.fillMaxSize().testTag(ALERT_PAGE_TEST_TAG)){
        Text(text = ALERT_PAGE, modifier = Modifier.align(Alignment.Center))
    }
}