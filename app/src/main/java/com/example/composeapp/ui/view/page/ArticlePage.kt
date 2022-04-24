package com.example.composeapp.ui.view.page

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composeapp.R
import com.example.composeapp.datasource.Article
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.example.composeapp.ui.view.article
import com.example.composeapp.ui.view.components.BackButton
import com.example.composeapp.ui.view.components.FavoriteButton
import com.example.composeapp.ui.view.components.RoundCornerImage
import com.example.composeapp.ui.view.components.RoundImage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val ARTICLE_PAGE = "ArticlePage"

@Composable
fun AppBar(
    height: Int,
    painter: Painter,
    backPress: () -> Unit = {}
) {
    Box(Modifier.height(height.dp)) {
        RoundCornerImage(
            painter = painter,
            modifier = Modifier.size((height - 16).dp)
        )
        FavoriteButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(0.dp, 0.dp, 16.dp, 0.dp)
        )
        BackButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(0.dp, 16.dp, 0.dp, 0.dp),
            32,
            painter = painterResource(id = R.drawable.ic_back_arrow_dark),
            backPress = backPress
        )
    }
}

@Composable
fun ArticleTitle(
    title: String,
    textSize: Int = 16,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = textSize.sp
        )
    }
}


@Composable
fun ArticleAuthor(
    name: String,
    timeCreated: LocalDateTime?,
    authorImagePainter: Painter
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundImage(
            painter = authorImagePainter, modifier = Modifier
                .size(64.dp)
                .padding(8.dp)
        )
        Column {
            Row(
                modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
            ) {
                Text(text = "By ")
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = (timeCreated ?: LocalDateTime.now()).format(
                    DateTimeFormatter
                        .ofPattern("MMMM dd, yyyy")
                ),
                modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
            )
        }
    }
}

@Composable
fun ArticleContent(message: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier

    ) {
        Text(
            text = message
        )
    }
}

@Composable
fun ArticlePage(
    article: Article,
    painter: Painter? = null,
    backPress: () -> Unit = {}
) {
    Column(
        modifier = Modifier.verticalScroll(ScrollState(0))
    ) {
        AppBar(
            height = 280,
            painter = painter ?: painterResource(id = R.drawable.android_background),
            backPress = backPress
        )
        ArticleTitle(
            title = article.title,
            textSize = 24,
            modifier = Modifier.padding(0.dp, 24.dp, 0.dp, 0.dp)
        )
        ArticleAuthor(
            name = article.author,
            timeCreated = article.timeCreated ?: LocalDateTime.now(),
            authorImagePainter = painterResource(id = R.drawable.download)
        )
        ArticleContent(
            message = article.contents ?: stringResource(id = R.string.message_string),
            modifier = Modifier.padding(16.dp)
        )
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DefaultArticlePreview() {
    ComposeAppTheme {
        ArticlePage(article)
    }
}
