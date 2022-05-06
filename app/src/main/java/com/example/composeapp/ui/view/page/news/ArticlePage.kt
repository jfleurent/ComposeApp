package com.example.composeapp.ui.view.page.news

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.composeapp.R
import com.example.composeapp.datasource.model.Article
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.example.composeapp.ui.view.components.BackButton
import com.example.composeapp.ui.view.components.FavoriteButton
import com.example.composeapp.ui.view.components.RoundCornerImage
import com.example.composeapp.ui.view.components.RoundImage
import com.example.composeapp.ui.view.testArticle
import com.example.composeapp.util.ellipsis
import com.example.composeapp.util.toLocalDataTime
import com.example.composeapp.viewmodel.ArticlePageViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val ARTICLE_PATH = "article"
const val ARTICLE_PAGE_PATH = "$NEWS_HOME_PAGE/{$ARTICLE_PATH}"

@Composable
fun AppBar(
    height: Int,
    painter: Painter,
    article: Article? = null,
    onFavorite: (Article) -> Unit = {},
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
                .padding(0.dp, 0.dp, 16.dp, 0.dp),
            favored = article?.favorite ?: false
        ) { article?.run(onFavorite) }
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
    authorImagePainter: Painter,
    authorImageSize: Int = 48,
    maxAuthorNameLength: Int = 10
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundImage(
            painter = authorImagePainter, modifier = Modifier
                .size(authorImageSize.dp)
                .padding(4.dp)
        )
        Column {
            Row(
                modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
            ) {
                if (name.isNotBlank()) {
                    Text(text = "By ")
                    Text(
                        text = name.ellipsis(maxAuthorNameLength),
                        fontWeight = FontWeight.Bold
                    )
                }
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
    articleId: Long,
    articlePageViewModel: ArticlePageViewModel? = null,
    backPress: () -> Unit = {}
) {
    Column(
        modifier = Modifier.verticalScroll(ScrollState(0))
    ) {
        val state: State<ArticlePageViewModel.UiState> =
            articlePageViewModel?.state?.collectAsState() ?: remember {
                mutableStateOf(ArticlePageViewModel.UiState(testArticle))
            }
        articlePageViewModel?.getArticle(articleId)
        AppBar(
            height = 280,
            painter = state.value.article.urlToImage?.let
            { rememberAsyncImagePainter(it) } ?: painterResource(
                id = R.drawable.android_background
            ),
            onFavorite = {
                articlePageViewModel?.favoriteArticle(it)
            },
            backPress = backPress,
            article = state.value.article,
        )
        ArticleTitle(
            title = state.value.article.title ?: "",
            textSize = 24,
            modifier = Modifier.padding(0.dp, 24.dp, 0.dp, 0.dp)
        )
        ArticleAuthor(
            name = state.value.article.author ?: "",
            timeCreated = state.value.article.publishedAt?.toLocalDataTime() ?: LocalDateTime.now(),
            authorImagePainter = painterResource(id = R.drawable.download),
            authorImageSize = 64,
            maxAuthorNameLength = 20
        )
        ArticleContent(
            message = state.value.article.content ?: "",
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
        ArticlePage(-1)
    }
}
