package com.example.composeapp.ui.view.components

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BlankCard(modifier: Modifier = Modifier, elevation: Int = 4, content: @Composable () -> Unit) {
    Surface(
        modifier = modifier,
        shadowElevation = elevation.dp,
        color = Color.Transparent,
        content = content
    )
}