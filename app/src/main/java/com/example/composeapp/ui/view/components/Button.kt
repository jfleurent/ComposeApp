package com.example.composeapp.ui.view.components

import android.util.MutableBoolean
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.composeapp.R


@Composable
fun ImageButton(
    modifier: Modifier = Modifier,
    size: Int,
    painter: Painter,
    description: String = "",
    iconColorFilter: ColorFilter? = null,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = description,
            modifier = Modifier.size(size.dp),
            colorFilter = iconColorFilter
        )
    }
}

@Composable
fun RoundIconButton(
    size: Int,
    painter: Painter,
    modifier: Modifier = Modifier,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    contentDescription: String = "",
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
    iconColorFilter: ColorFilter? = null,
    buttonPress: () -> Unit = {}
) {
    Button(
        onClick = buttonPress,
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp, 0.dp, 0.dp, 0.dp),
        elevation = elevation,
        colors = colors,
        modifier = modifier.size(size.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.size((size / 2).dp),
            colorFilter = iconColorFilter
        )
    }
}

@Composable
fun FavoriteButton(modifier: Modifier, size: Int = 48, favored : Boolean,onClick: () -> Unit = {}) {
    RoundIconButton(
        modifier = modifier,
        size = size,
        painter = painterResource(id = if (!favored) R.drawable.ic_not_favorite else R.drawable.ic_favorite),
        contentDescription = "Favorite",
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
        buttonPress = onClick
    )
}

@Composable
fun BackButton(
    modifier: Modifier, size: Int,
    painter: Painter,
    backPress: () -> Unit
) {
    ImageButton(
        modifier = modifier,
        size = size,
        painter = painter,
        description = "Back Button",
        onClick = backPress
    )
}