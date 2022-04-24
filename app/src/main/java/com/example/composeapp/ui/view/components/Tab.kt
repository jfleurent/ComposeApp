package com.example.composeapp.ui.view.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeapp.R
import com.example.composeapp.ui.theme.ComposeAppTheme

sealed class TabItem {
    data class TextTabItem(
        val label: String,
        var selected: Boolean = false,
        val textSize: Int,
        val onClick: () -> Unit = {}
    ) : TabItem()

    data class ImageTabItem(
        val label: String = "",
        var selected: Boolean = false,
        val modifier: Modifier = Modifier,
        val painter: Painter,
        val textSize: Int = 0,
        val onClick: () -> Unit = {}
    ) : TabItem()
}

@Composable
fun Tab(
    tabItem: TabItem.TextTabItem,
    onTabClick: () -> Unit = {}
) {
    Button(
        onClick = { onTabItemClick(tabItem.onClick, onTabClick) },
        colors = buttonColors(containerColor = Color.Transparent)
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
        ) {
            Text(
                text = tabItem.label,
                fontSize = tabItem.textSize.sp,
                color = MaterialTheme.colorScheme.primary
            )
            if (tabItem.selected) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}

@Composable
fun ImageTab(
    tabItem: TabItem.ImageTabItem,
    onTabClick: () -> Unit = {}
) {
    Button(
        onClick = { onTabItemClick(tabItem.onClick, onTabClick) },
        colors = buttonColors(containerColor = Color.Transparent)
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
        ) {
            val alpha = if (tabItem.selected) 1.0f else 0.5f
            Image(
                painter = tabItem.painter,
                contentDescription = "Image Tab",
                alpha = alpha,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = tabItem.modifier
                    .size(40.dp)
                    .then(tabItem.modifier)
            )
            Text(
                text = tabItem.label,
                fontSize = tabItem.textSize.sp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = alpha)
            )
        }
    }
}

private fun onTabItemClick(onClick: () -> Unit, onTabClick: () -> Unit) {
    onClick.invoke()
    onTabClick.invoke()
}

@Composable
fun TabRow(
    items: List<TabItem>,
    modifier: Modifier = Modifier,
) {
    var tabIndex by remember { mutableStateOf(0) }
    Row(modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.Center) {
        items.forEachIndexed { index, tabItem ->
            when (tabItem) {
                is TabItem.TextTabItem ->
                    Tab(
                        tabItem = tabItem.apply { selected = index == tabIndex },
                    )
                    {
                        tabIndex = index
                    }
                is TabItem.ImageTabItem ->
                    ImageTab(
                        tabItem = tabItem.apply { selected = index == tabIndex },
                    ) {
                        tabIndex = index
                    }
            }

        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DefaultTabPreview() {
    ComposeAppTheme {
        TabRow(
            listOf(
                TabItem.ImageTabItem(
                    label = "Tab 1",
                    painter = painterResource(id = R.drawable.ic_home),
                    modifier = Modifier.size(32.dp),
                    textSize = 12
                ),
                TabItem.ImageTabItem(
                    label = "Tab 2",
                    painter = painterResource(id = R.drawable.ic_shopping),
                    modifier = Modifier.size(32.dp),
                    textSize = 12
                ),
                TabItem.ImageTabItem(
                    label = "Tab 3",
                    painter = painterResource(id = R.drawable.ic_alert),
                    modifier = Modifier.size(32.dp),
                    textSize = 12
                ),
                TabItem.ImageTabItem(
                    label = "Tab 4",
                    painter = painterResource(id = R.drawable.ic_profile),
                    modifier = Modifier.size(32.dp),
                    textSize = 12
                )
            )
        )
    }
}
