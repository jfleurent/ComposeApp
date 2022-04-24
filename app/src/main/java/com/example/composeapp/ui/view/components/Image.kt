package com.example.composeapp.ui.view.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.composeapp.R

@Composable
fun RoundCornerImage(painter: Painter, modifier: Modifier) {
    Image(
        painter = painter,
        contentDescription = "background",
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .clip(
                RoundedCornerShape(
                    0,
                    0,
                    0,
                    15
                )
            ),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun RoundImage(painter: Painter, modifier: Modifier) {
    Image(
        painter = painter,
        contentDescription = "Author",
        modifier = Modifier
            .then(modifier)
            .clip(CircleShape)
    )
}