package com.example.composeapp.ui.view.page

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.R
import com.example.composeapp.ui.view.components.ImageButton
import com.example.composeapp.ui.view.components.RoundIconButton
import com.example.composeapp.ui.view.components.TabItem
import com.example.composeapp.ui.view.components.TabRow
import com.example.composeapp.viewmodel.ArticlePageViewModel
import com.example.composeapp.viewmodel.HomePageViewModel
import com.example.composeapp.viewmodel.LandingPageViewModel

const val LANDING_PAGE = "LandingPage"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingPage(
    homePageViewModel: HomePageViewModel,
    articlePageViewModel: ArticlePageViewModel,
    landingPageViewModel: LandingPageViewModel,
    onBackPressed: () -> Unit
) {
    val navController = rememberNavController()
    val state: State<LandingPageViewModel.UiState> = landingPageViewModel.state.collectAsState()
    val context = LocalContext.current
    Box {
        Scaffold(
            topBar = { if (state.value.displayingTabs) AppBar() },
            bottomBar = {
                if (state.value.displayingTabs)
                    BottomNavigation(
                        selectedLabel = state.value.bottomTab.name,
                        items = listOf(
                            TabItem.ImageTabItem(
                                label = LandingPageViewModel.BottomTab.HOME.name,
                                painter = painterResource(id = R.drawable.ic_home),
                                onClick = {
                                    landingPageViewModel.setActiveTab(
                                        LandingPageViewModel.BottomTab.HOME
                                    )
                                    navController.navigate(HOME_PAGE)
                                }),
                            TabItem.ImageTabItem(
                                label = LandingPageViewModel.BottomTab.SHOPPING.name,
                                painter = painterResource(id = R.drawable.ic_shopping),
                                onClick = {
                                    landingPageViewModel.setActiveTab(
                                        LandingPageViewModel.BottomTab.SHOPPING
                                    )
                                    navController.navigate(SHOPPING_PAGE)
                                }),
                            TabItem.ImageTabItem(
                                label = LandingPageViewModel.BottomTab.ALERT.name,
                                painter = painterResource(id = R.drawable.ic_alert),
                                onClick = {
                                    landingPageViewModel.setActiveTab(
                                        LandingPageViewModel.BottomTab.ALERT
                                    )
                                    navController.navigate(ALERT_PAGE)
                                }),
                            TabItem.ImageTabItem(
                                label = LandingPageViewModel.BottomTab.PROFILE.name,
                                painter = painterResource(id = R.drawable.ic_profile),
                                onClick = {
                                    landingPageViewModel.setActiveTab(
                                        LandingPageViewModel.BottomTab.PROFILE
                                    )
                                    navController.navigate(PROFILE_PAGE)
                                })
                        )
                    )
            },
        ) {
            NavHost(
                modifier = Modifier.padding(it),
                navController = navController,
                startDestination = HOME_PAGE) {
                composable(HOME_PAGE) {
                    landingPageViewModel.displayTabs(true)
                    HomePage(navController, homePageViewModel)
                }
                composable(ALERT_PAGE){
                    landingPageViewModel.displayTabs(true)
                    AlertPage()
                }
                composable(PROFILE_PAGE){
                    landingPageViewModel.displayTabs(true)
                    ProfilePage()
                }
                composable(SHOPPING_PAGE){
                    landingPageViewModel.displayTabs(true)
                    ShoppingPage()
                }
                composable(ARTICLE_PAGE_PATH) {
                    landingPageViewModel.displayTabs(false)
                    val articleId: Long =
                        it.arguments?.get(ARTICLE_PATH).toString().toLong()
                    ArticlePage(articleId, articlePageViewModel) { onBackPressed() }
                    argument(ARTICLE_PATH) {
                        type = NavType.IntType
                    }
                }
            }
        }
        if (state.value.displayingTabs)
            FabButton(
                modifier = Modifier
                    .padding(32.dp, 0.dp, 32.dp, 32.dp)
                    .align(Alignment.BottomCenter)
            ){
                Toast.makeText(context,"Clicked on FAB", Toast.LENGTH_SHORT).show()
            }
    }
}

@Composable
fun FabButton(modifier: Modifier, onButtonPress: () -> Unit) {
    RoundIconButton(
        size = 48,
        painter = painterResource(id = R.drawable.ic_add),
        modifier = modifier,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        buttonPress = onButtonPress
    )
}

@Composable
fun AppBar(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        ImageButton(
            modifier = Modifier
                .padding(8.dp,24.dp,0.dp,0.dp)
                .align(Alignment.CenterStart),
            size = 32,
            painter = painterResource(id = R.drawable.ic_nav_drawer),
            description = "Nav Drawer"
        )
        ImageButton(
            modifier = Modifier
                .padding(0.dp,24.dp,8.dp,0.dp)
                .align(Alignment.CenterEnd),
            size = 32,
            painter = painterResource(id = R.drawable.ic_search),
            description = "Search"
        )
    }
}

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    selectedLabel: String,
    items: List<TabItem.ImageTabItem>
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(color = if (isSystemInDarkTheme()) Color.Black else Color.White)
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)
        ) {}
        TabRow(
            items = items,
            selectedLabel = selectedLabel,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}