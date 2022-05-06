package com.example.composeapp.ui.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    selectedLabel: String,
    items: List<TabItem.ImageTabItem>
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(color = if (isSystemInDarkTheme()) Color.Black else Color.White)
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)
        ) {}
        TabRow(
            items = items,
            selectedLabel = selectedLabel,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}