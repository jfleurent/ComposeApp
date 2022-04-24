package com.example.composeapp.ui.view.page

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.R
import com.example.composeapp.datasource.Article
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.example.composeapp.ui.view.*
import com.example.composeapp.ui.view.components.*
import com.example.composeapp.util.ellipsis

const val HOME_PAGE = "HomePage"

@Composable
fun AppBar(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        ImageButton(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopStart),
            size = 32,
            painter = painterResource(id = R.drawable.ic_nav_drawer),
            description = "Nav Drawer"
        )
        ImageButton(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopEnd),
            size = 32,
            painter = painterResource(id = R.drawable.ic_search),
            description = "Search"
        )
    }
}


@Composable
fun ArticleItemList(
    articles: List<Article>,
    height: Int,
    width: Int,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(0.dp, 8.dp, 0.dp, 0.dp)
            .then(modifier)
    ) {
        articles.forEach {
            ArticleItem(height = height, width = width, article = it, navController = navController)
        }
    }
}


@Composable
fun ArticleCard(modifier: Modifier, article: Article) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(4))
            .clipToBounds()
            .then(modifier)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            ArticleAuthor(
                name = article.author,
                timeCreated = article.timeCreated,
                authorImagePainter = painterResource(id = R.drawable.download)
            )
            FavoriteButton(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(0.dp, 8.dp, 16.dp, 0.dp)
            )
        }
        ArticleTitle(title = article.title.ellipsis(30))
        ArticleContent(
            message = (article.contents
                ?: stringResource(id = R.string.message_string)).ellipsis(30),
            modifier = Modifier.padding(8.dp)
        )
    }
}



@Composable
fun ArticleCardWithImage(
    article: Article,
    navController: NavController,
    size : Int,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(4))
                .clipToBounds()
                .clickable {
                    navController.navigate(ARTICLE_PAGE) {
                        launchSingleTop = true
                    }
                }
                .background(color = if (isSystemInDarkTheme()) Color.Black else Color.White)
                .align(Alignment.CenterEnd)
                .width(size.dp)
                .padding(0.dp, 0.dp, 0.dp, 16.dp)
        ) {
            Column(modifier = Modifier.padding((size/3).dp, 0.dp, 0.dp, 0.dp)) {
                Box(Modifier.fillMaxWidth()) {
                    ArticleAuthor(
                        name = article.author,
                        timeCreated = article.timeCreated,
                        authorImagePainter = painterResource(id = R.drawable.download)
                    )
                    FavoriteButton(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(0.dp, 8.dp, 16.dp, 0.dp),
                        size = (size/11.25).toInt()
                    )
                }
                ArticleTitle(title = article.title.ellipsis(20))
                ArticleContent(
                    message = (article.contents
                        ?: stringResource(id = R.string.message_string)).ellipsis(20),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Card {
            Image(
                painter = painterResource(id = R.drawable.android_background),
                contentDescription = "Background",
                modifier = Modifier
                    .size((size/3).dp, (size * .38).dp)
                    .clip(RoundedCornerShape(4))
                    .align(Alignment.TopStart),
                contentScale = ContentScale.FillBounds
            )
        }

    }

}


@Composable
fun ArticleItem(height: Int, width: Int, article: Article, navController: NavController) {
    Box(
        modifier = Modifier
            .size(width.dp, height.dp)
            .padding(8.dp)
            .clickable {
                navController.navigate(ARTICLE_PAGE) {
                    launchSingleTop = true
                }
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.android_background),
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
            article = article
        )
    }
}


@Composable
fun RecommendedList(articles: List<Article>, navController: NavController) {
    articles.forEach {
        ArticleCardWithImage(
            article = it,
            navController = navController,
            size = 360,
            modifier = Modifier
                .height(176.dp)
                .fillMaxWidth()
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
fun BottomNavigation(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
    ) {
        Row(
            modifier = Modifier
                .background(color = if (isSystemInDarkTheme()) Color.Black else Color.White)
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)
        ) {}
        TabRow(
            items = listOf(
                TabItem.ImageTabItem(painter = painterResource(id = R.drawable.ic_home)),
                TabItem.ImageTabItem(painter = painterResource(id = R.drawable.ic_shopping)),
                TabItem.ImageTabItem(painter = painterResource(id = R.drawable.ic_alert)),
                TabItem.ImageTabItem(painter = painterResource(id = R.drawable.ic_profile))
            ),
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        RoundIconButton(
            size = 48,
            painter = painterResource(id = R.drawable.ic_add),
            modifier = Modifier
                .padding(32.dp, 0.dp, 32.dp, 32.dp)
                .align(Alignment.BottomCenter),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
            colors = buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        )
    }
}


@Composable
fun HomePage(navController: NavController) {
    Box() {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
                .padding(0.dp, 0.dp, 0.dp, 64.dp)
        ) {
            AppBar()
            TabRow(
                items = listOf(
                    TabItem.TextTabItem(label = "Latest", textSize = 20),
                    TabItem.TextTabItem(label = "Decorative", textSize = 20),
                    TabItem.TextTabItem(label = "Music", textSize = 20),
                    TabItem.TextTabItem(label = "Style", textSize = 20),
                    TabItem.TextTabItem(label = "Technology", textSize = 20),
                    TabItem.TextTabItem(label = "Business", textSize = 20),
                )
            )
            ArticleItemList(
                articles = listOf(article, article, article),
                height = 240,
                width = 360,
                navController = navController
            )
            Text(
                text = "Recommended",
                fontSize = 20.sp,
                fontWeight = Bold,
                modifier = Modifier.padding(16.dp)
            )
            RecommendedList(
                articles = listOf(article, article, article, article, article, article),
                navController = navController
            )
        }
        BottomNavigation(modifier = Modifier.align(Alignment.BottomCenter))
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