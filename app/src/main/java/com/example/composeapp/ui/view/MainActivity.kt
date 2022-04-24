package com.example.composeapp.ui.view

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.datasource.Article
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.example.composeapp.ui.view.page.ARTICLE_PAGE
import com.example.composeapp.ui.view.page.ArticlePage
import com.example.composeapp.ui.view.page.HOME_PAGE
import com.example.composeapp.ui.view.page.HomePage

val article = Article(
    "A News Article About Android",
    "Bob Ross"
)

class MainActivity : ComponentActivity() {

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAppTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = HOME_PAGE) {
                        composable(HOME_PAGE) { HomePage(navController) }
                        composable(ARTICLE_PAGE) {
                            ArticlePage(article) { onBackPressed() }
                        }
                    }
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
fun DefaultPreview() {
    ComposeAppTheme {
        ArticlePage(article)
    }
}
