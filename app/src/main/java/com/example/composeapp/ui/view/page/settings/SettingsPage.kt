package com.example.composeapp.ui.view.page.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

const val SETTINGS_PAGE = "Settings"

//Testing
const val SETTING_PAGE_TEST_TAG = "setting_page_test_tag"

@Composable
fun SettingsPage() {
    Box(modifier = Modifier.fillMaxSize().testTag(SETTING_PAGE_TEST_TAG)) {
        Text(text = SETTINGS_PAGE, modifier = Modifier.align(Alignment.Center))
    }
}