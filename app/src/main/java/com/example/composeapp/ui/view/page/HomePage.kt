package com.example.composeapp.ui.view.page

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberAsyncImagePainter
import com.example.composeapp.R
import com.example.composeapp.datasource.model.Article
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.example.composeapp.ui.view.*
import com.example.composeapp.ui.view.components.*
import com.example.composeapp.util.ellipsis
import com.example.composeapp.util.toLocalDataTime
import com.example.composeapp.viewmodel.HomePageViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import okhttp3.internal.wait

const val HOME_PAGE = "HomePage"

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
        articles.forEach {
            item(key = it.primaryKey) {
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
    val coroutineScope = rememberCoroutineScope()
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
                .width(size.dp)
                .padding(0.dp, 0.dp, 0.dp, 16.dp)
        ) {
            Column(modifier = Modifier.padding((size / 3).dp, 0.dp, 0.dp, 0.dp)) {
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
    articles: List<Article>,
    onNavigate: (Article) -> Unit,
    onFavorite: (Article) -> Unit
) {
    articles.forEach {
        ArticleCardWithImage(
            article = it,
            size = 360,
            modifier = Modifier
                .height(176.dp)
                .fillMaxWidth(),
            onFavorite = onFavorite,
            onNavigate = onNavigate
        )
        Row(
            modifier = Modifier
                .height(16.dp)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth()
        ) {}
    }
}

@Composable
fun HomePage(
    navController: NavController,
    homePageViewModel: HomePageViewModel? = null,
) {
    val state: State<HomePageViewModel.UiState> = homePageViewModel?.state?.collectAsState()
        ?: remember {
            mutableStateOf(
                HomePageViewModel.UiState(
                    latestFeed = listOf(testArticle, testArticle, testArticle),
                    recommendedFeed = listOf(testArticle, testArticle, testArticle)
                )
            )
        }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        TabRow(
            selectedLabel = state.value.activeTab.label,
            items = listOf(
                TabItem.TextTabItem(
                    label = "Latest",
                    textSize = 20,
                    onClick = { homePageViewModel?.setActiveTab(HomePageViewModel.TabPage.LATEST) }),
                TabItem.TextTabItem(
                    label = "Decorative",
                    textSize = 20,
                    onClick = { homePageViewModel?.setActiveTab(HomePageViewModel.TabPage.DECORATIVE) }),
                TabItem.TextTabItem(
                    label = "Music",
                    textSize = 20,
                    onClick = { homePageViewModel?.setActiveTab(HomePageViewModel.TabPage.MUSIC) }),
                TabItem.TextTabItem(
                    label = "Style",
                    textSize = 20,
                    onClick = { homePageViewModel?.setActiveTab(HomePageViewModel.TabPage.STYLE) }),
                TabItem.TextTabItem(
                    label = "Technology",
                    textSize = 20,
                    onClick = { homePageViewModel?.setActiveTab(HomePageViewModel.TabPage.TECHNOLOGY) }),
                TabItem.TextTabItem(
                    label = "Business",
                    textSize = 20,
                    onClick = { homePageViewModel?.setActiveTab(HomePageViewModel.TabPage.BUSINESS) }),
            )
        )
        ArticleItemList(
            articles = state.value.latestFeed,
            height = 240,
            width = 360,
            onNavigate = {
                navController.navigate("$HOME_PAGE/${it.primaryKey}") {
                    launchSingleTop = true
                }
            },
            onFavorite = {
                homePageViewModel?.favoriteArticle(it)
            })
        Text(
            text = "Recommended",
            fontSize = 20.sp,
            fontWeight = Bold,
            modifier = Modifier.padding(16.dp)
        )
        RecommendedList(
            articles = state.value.recommendedFeed,
            onNavigate = {
                navController.navigate("$HOME_PAGE/${it.primaryKey}") {
                    launchSingleTop = true
                }
            },
            onFavorite = {
                homePageViewModel?.favoriteArticle(it)
            })
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
        HomePage(navController = navController)
    }
}