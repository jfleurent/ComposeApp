package com.example.composeapp.ui.view.components

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeapp.R

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    searchText : String,
    visible : Boolean,
    onTextChange : (String) -> Unit,
    onVisibilityChange : (Boolean) -> Unit,
    onSearch: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = visible,
            enter = slideInHorizontally(
                initialOffsetX = { 500 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { 500 },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            )
        ) {
            TextField(
                value = searchText,
                onValueChange = onTextChange,
                singleLine = true,
                label = { Text(text = "Search") },
                keyboardActions = KeyboardActions(onAny = {
                    focusManager.clearFocus()
                    onSearch(searchText)
                }),
                trailingIcon = {
                    ImageButton(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        size = 32,
                        painter = painterResource(id = R.drawable.ic_search),
                        description = "Search"
                    ) {
                        onVisibilityChange(visible)
                    }
                }
            )
        }
        if (!visible)
            ImageButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                size = 32,
                painter = painterResource(id = R.drawable.ic_search),
                description = "Search"
            ) {
                onVisibilityChange(visible)
            }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SearchTextFieldPreview() {
    SearchTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        searchText = "",
        visible = false,
        onTextChange = { },
        onVisibilityChange = { },
        onSearch = {}
    )
}