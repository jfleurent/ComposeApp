package com.example.composeapp.ui.view.page.news

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.composeapp.R
import com.example.composeapp.datasource.model.Article
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.example.composeapp.ui.view.*
import com.example.composeapp.ui.view.components.*
import com.example.composeapp.util.ellipsis
import com.example.composeapp.util.toLocalDataTime
import com.example.composeapp.viewmodel.NewsHomePageViewModel
import com.example.composeapp.viewmodel.LandingPageViewModel

const val NEWS_HOME_PAGE = "News"

//Testing
const val NEWS_PAGE_TEST_TAG = "news_page_test_tag"
const val NEWS_PAGE_TAB_ROW_TAG = "news_page_tab_row"
const val NEWS_PAGE_LATEST_ARTICLES_TAG = "news_page_latest_articles"
const val NEWS_PAGE_RECOMMENDED_TITLE_TAG = "news_page_recommended_title"
const val NEWS_PAGE_RECOMMENDED_ARTICLES_TAG = "news_page_recommended_articles"

@Composable
fun ArticleItemList(
    articles: List<Article>,
    height: Int,
    width: Int,
    modifier: Modifier = Modifier,
    onNavigate: (Article) -> Unit,
    onFavorite: (Article) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .padding(0.dp, 8.dp, 0.dp, 0.dp)
            .then(modifier)
    ) {
        articles.forEachIndexed { index, it ->
            item(key = "${it.primaryKey}_$index") {
                ArticleItem(
                    height = height,
                    width = width,
                    article = it,
                    onNavigate = onNavigate,
                    onFavorite = onFavorite
                )
            }
        }
    }
}


@Composable
fun ArticleCard(
    modifier: Modifier,
    article: Article,
    onFavorite: (Article) -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(4))
            .clipToBounds()
            .then(modifier)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            ArticleAuthor(
                name = article.author ?: "",
                timeCreated = article.publishedAt?.toLocalDataTime(),
                authorImagePainter = painterResource(id = R.drawable.download),
                authorImageSize = 64,
                maxAuthorNameLength = 20
            )
            FavoriteButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(0.dp, 8.dp, 16.dp, 0.dp),
                favored = article.favorite
            ) { onFavorite.invoke(article) }
        }
        ArticleTitle(title = article.title?.ellipsis(30) ?: "")
        ArticleContent(
            message = (article.content
                ?: stringResource(id = R.string.message_string)).ellipsis(30),
            modifier = Modifier.padding(8.dp)
        )
    }
}


@Composable
fun ArticleCardWithImage(
    article: Article,
    size: Int,
    modifier: Modifier = Modifier,
    onNavigate: (Article) -> Unit,
    onFavorite: (Article) -> Unit
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(4))
                .clipToBounds()
                .clickable { onNavigate.invoke(article) }
                .background(color = if (isSystemInDarkTheme()) Color.Black else Color.White)
                .align(Alignment.CenterEnd)
                .fillMaxWidth(.95f)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding((size / 3).dp, 0.dp, 0.dp, 0.dp)
            ) {
                Box(Modifier.fillMaxWidth()) {
                    ArticleAuthor(
                        name = article.author ?: "",
                        timeCreated = article.publishedAt?.toLocalDataTime(),
                        authorImagePainter = painterResource(id = R.drawable.download)
                    )
                    FavoriteButton(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(0.dp, 8.dp, 16.dp, 0.dp),
                        size = (size / 11.25).toInt(),
                        favored = article.favorite
                    ) {
                        onFavorite.invoke(article)
                    }
                }
                ArticleTitle(title = article.title?.ellipsis(20) ?: "")
                ArticleContent(
                    message = (article.content
                        ?: stringResource(id = R.string.message_string)).ellipsis(20),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        BlankCard {
            Image(
                painter = article.urlToImage?.let { rememberAsyncImagePainter(it) }
                    ?: painterResource(
                        id = R.drawable.android_background
                    ),
                contentDescription = "Background",
                modifier = Modifier
                    .size((size / 3).dp, (size * .38).dp)
                    .clip(RoundedCornerShape(4))
                    .align(Alignment.TopStart),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}


@Composable
fun ArticleItem(
    height: Int,
    width: Int,
    article: Article,
    onNavigate: (Article) -> Unit,
    onFavorite: (Article) -> Unit
) {
    Box(
        modifier = Modifier
            .size(width.dp, height.dp)
            .padding(8.dp)
            .clickable { onNavigate.invoke(article) }
    ) {
        Image(
            painter = article.urlToImage?.let { rememberAsyncImagePainter(it) } ?: painterResource(
                id = R.drawable.android_background
            ),
            contentDescription = "Background",
            modifier = Modifier
                .clip(RoundedCornerShape(4))
                .size(width.dp, (height * 0.6).dp),
            contentScale = ContentScale.FillBounds
        )
        ArticleCard(
            modifier = Modifier
                .size((width * .9).dp, (height * .6).dp)
                .background(color = if (isSystemInDarkTheme()) Color.Black else Color.White)
                .align(Alignment.BottomCenter),
            article = article,
            onFavorite = onFavorite
        )
    }
}


@Composable
fun RecommendedList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onNavigate: (Article) -> Unit,
    onFavorite: (Article) -> Unit
) {
    articles.forEach {
        ArticleCardWithImage(
            article = it,
            size = 360,
            modifier = modifier
                .height(176.dp)
                .padding(8.dp, 0.dp, 8.dp, 0.dp)
                .fillMaxWidth(),
            onFavorite = onFavorite,
            onNavigate = onNavigate
        )
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth()
        )
    }
}

@Composable
fun NewsHomePage(
    navController: NavController,
    newsHomePageViewModel: NewsHomePageViewModel? = null,
    landingPageViewModel: LandingPageViewModel? = null
) {
    val state: State<NewsHomePageViewModel.UiState> = newsHomePageViewModel?.state?.collectAsState()
        ?: remember {
            mutableStateOf(
                NewsHomePageViewModel.UiState(
                    latestFeed = listOf(testArticle, testArticle, testArticle),
                    recommendedFeed = listOf(testArticle, testArticle, testArticle)
                )
            )
        }
    LazyColumn(modifier = Modifier.testTag(NEWS_PAGE_TEST_TAG)) {
        item {
            TabRow(
                modifier = Modifier.testTag(NEWS_PAGE_TAB_ROW_TAG),
                selectedLabel = state.value.activeTab.label,
                items = listOf(
                    TabItem.TextTabItem(
                        label = "Latest",
                        textSize = 20,
                        onClick = { newsHomePageViewModel?.setActiveTab(NewsHomePageViewModel.TabPage.LATEST) }),
                    TabItem.TextTabItem(
                        label = "Decorative",
                        textSize = 20,
                        onClick = { newsHomePageViewModel?.setActiveTab(NewsHomePageViewModel.TabPage.DECORATIVE) }),
                    TabItem.TextTabItem(
                        label = "Music",
                        textSize = 20,
                        onClick = { newsHomePageViewModel?.setActiveTab(NewsHomePageViewModel.TabPage.MUSIC) }),
                    TabItem.TextTabItem(
                        label = "Style",
                        textSize = 20,
                        onClick = { newsHomePageViewModel?.setActiveTab(NewsHomePageViewModel.TabPage.STYLE) }),
                    TabItem.TextTabItem(
                        label = "Technology",
                        textSize = 20,
                        onClick = { newsHomePageViewModel?.setActiveTab(NewsHomePageViewModel.TabPage.TECHNOLOGY) }),
                    TabItem.TextTabItem(
                        label = "Business",
                        textSize = 20,
                        onClick = { newsHomePageViewModel?.setActiveTab(NewsHomePageViewModel.TabPage.BUSINESS) }),
                )
            )
        }
        item {
            ArticleItemList(
                modifier = Modifier.testTag(NEWS_PAGE_LATEST_ARTICLES_TAG),
                articles = state.value.latestFeed,
                height = 240,
                width = 360,
                onNavigate = {
                    landingPageViewModel?.pushCurrentState(ARTICLE_PAGE_PATH)
                    navController.navigate("$NEWS_HOME_PAGE/${it.primaryKey}") {
                        launchSingleTop = true
                    }
                },
                onFavorite = {
                    newsHomePageViewModel?.favoriteArticle(it)
                })
        }
        item {
            Text(
                text = "Recommended",
                fontSize = 20.sp,
                fontWeight = Bold,
                modifier = Modifier.padding(16.dp).testTag(NEWS_PAGE_RECOMMENDED_TITLE_TAG)
            )
        }
        item {
            RecommendedList(
                modifier = Modifier.testTag(NEWS_PAGE_RECOMMENDED_ARTICLES_TAG),
                articles = state.value.recommendedFeed,
                onNavigate = {
                    landingPageViewModel?.pushCurrentState(ARTICLE_PAGE_PATH)
                    navController.navigate("$NEWS_HOME_PAGE/${it.primaryKey}") {
                        launchSingleTop = true
                    }
                },
                onFavorite = {
                    newsHomePageViewModel?.favoriteArticle(it)
                })
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DefaultHomePreview() {
    ComposeAppTheme {
        val navController = rememberNavController()
        NewsHomePage(navController = navController)
    }
}