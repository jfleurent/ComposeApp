package com.example.composeapp.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composeapp.datasource.model.Article
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.example.composeapp.ui.view.page.*
import com.example.composeapp.ui.view.page.news.ArticlePage
import com.example.composeapp.viewmodel.ArticlePageViewModel
import com.example.composeapp.viewmodel.HomePageViewModel
import com.example.composeapp.viewmodel.LandingPageViewModel
import dagger.hilt.android.AndroidEntryPoint

val testArticle = Article(
    primaryKey = -1,
    "A News Article About Android",
    "Bob Ross"
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val articlePageViewModel: ArticlePageViewModel by viewModels()
    private val homePageViewModel: HomePageViewModel by viewModels()
    private val landingPageViewModel : LandingPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LandingPage(
                        homePageViewModel = homePageViewModel,
                        articlePageViewModel = articlePageViewModel,
                        landingPageViewModel = landingPageViewModel,
                    ) {
                        onBackPressed()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        landingPageViewModel.onBack()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    ComposeAppTheme {
        ArticlePage(-1)
    }
}
