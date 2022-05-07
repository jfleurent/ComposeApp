package com.example.composeapp.ui.view.page

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.R
import com.example.composeapp.ui.view.components.*
import com.example.composeapp.ui.view.page.movies.*
import com.example.composeapp.ui.view.page.music.MUSIC_HOME_PAGE
import com.example.composeapp.ui.view.page.music.MusicHomePage
import com.example.composeapp.ui.view.page.news.*
import com.example.composeapp.ui.view.page.photos.PHOTO_HOME_PAGE
import com.example.composeapp.ui.view.page.photos.PhotoHomePage
import com.example.composeapp.ui.view.page.settings.SETTINGS_PAGE
import com.example.composeapp.ui.view.page.settings.SettingsPage
import com.example.composeapp.viewmodel.ArticlePageViewModel
import com.example.composeapp.viewmodel.HomePageViewModel
import com.example.composeapp.viewmodel.LandingPageViewModel

const val LANDING_PAGE = "LandingPage"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingPage(
    homePageViewModel: HomePageViewModel? = null,
    articlePageViewModel: ArticlePageViewModel? = null,
    landingPageViewModel: LandingPageViewModel? = null,
    onBackPressed: () -> Unit = {}
) {
    val navController = rememberNavController()
    val state: State<LandingPageViewModel.UiState> =
        landingPageViewModel?.state?.collectAsState() ?: remember {
            mutableStateOf(LandingPageViewModel.UiState())
        }
    val context = LocalContext.current
    ModalNavigationDrawer(
        drawerContent = {
            DrawerContentLayout(
                modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 0.dp),
                selectedLabel = state.value.drawerState.selectedLabel,
                content = DrawerContent(
                    header = {
                        ProfileDrawerHeader(
                            name = "Bob Ross",
                            email = "bob_ross42@yahoo.com",
                            painter = painterResource(id = R.drawable.download)
                        )
                    },
                    items = listOf(
                        listOf(
                            DrawerItem(
                                icon = painterResource(id = R.drawable.ic_news),
                                label = LandingPageViewModel.TopLevelPage.NEWS.label,
                                onClick = {
                                    landingPageViewModel?.pushCurrentState(LandingPageViewModel.TopLevelPage.NEWS.label)
                                    landingPageViewModel?.setCurrentPage(LandingPageViewModel.TopLevelPage.NEWS)
                                    state.value.currentPageState.currentTab?.run {
                                        navController.navigate(
                                            destination
                                        ) {
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            ),
                            DrawerItem(
                                icon = painterResource(id = R.drawable.ic_movie),
                                label = LandingPageViewModel.TopLevelPage.MOVIE.label,
                                onClick = {
                                    landingPageViewModel?.pushCurrentState(LandingPageViewModel.TopLevelPage.MOVIE.label)
                                    landingPageViewModel?.setCurrentPage(LandingPageViewModel.TopLevelPage.MOVIE)
                                    navController.navigate(MOVIE_HOME_PAGE) {
                                        launchSingleTop = true
                                    }
                                }
                            ),
                            DrawerItem(
                                icon = painterResource(id = R.drawable.ic_photos),
                                label = LandingPageViewModel.TopLevelPage.PHOTOS.label,
                                onClick = {
                                    landingPageViewModel?.pushCurrentState(LandingPageViewModel.TopLevelPage.PHOTOS.label)
                                    landingPageViewModel?.setCurrentPage(LandingPageViewModel.TopLevelPage.PHOTOS)
                                    navController.navigate(PHOTO_HOME_PAGE) {
                                        launchSingleTop = true
                                        restoreState = true

                                    }
                                }
                            ),
                            DrawerItem(
                                icon = painterResource(id = R.drawable.ic_music),
                                label = LandingPageViewModel.TopLevelPage.MUSIC.label,
                                onClick = {
                                    landingPageViewModel?.pushCurrentState(LandingPageViewModel.TopLevelPage.MUSIC.label)
                                    landingPageViewModel?.setCurrentPage(LandingPageViewModel.TopLevelPage.MUSIC)
                                    navController.navigate(MUSIC_HOME_PAGE) {
                                        launchSingleTop = true
                                    }
                                }
                            )
                        ),
                        listOf(
                            DrawerItem(
                                icon = painterResource(id = R.drawable.ic_setting),
                                label = LandingPageViewModel.TopLevelPage.SETTINGS.label,
                                onClick = {
                                    landingPageViewModel?.pushCurrentState(LandingPageViewModel.TopLevelPage.SETTINGS.label)
                                    landingPageViewModel?.setCurrentPage(LandingPageViewModel.TopLevelPage.SETTINGS)
                                    navController.navigate(SETTINGS_PAGE) {
                                        launchSingleTop = true
                                    }
                                }
                            )
                        )
                    )
                )
            ) {
                landingPageViewModel?.setSelectDrawerItem(it)
            }
        },
        drawerState = state.value.drawerState.visibleState
    ) {
        Box {
            Scaffold(
                topBar = {
                    if (state.value.appbarState.topBarVisible) AppBar(
                        searchText = state.value.searchFieldState.searchText,
                        visible = state.value.searchFieldState.searchViewVisible,
                        onTextChange = { landingPageViewModel?.setSearchText(it) },
                        onVisibilityChange = { landingPageViewModel?.setSearchViewVisible(!it) },
                        onSearch = {
                            when (state.value.currentPageState.page) {
                                LandingPageViewModel.TopLevelPage.NEWS -> homePageViewModel?.searchArticles(
                                    it
                                )
                                else -> {}
                            }
                        },
                        onDrawerOpen = { landingPageViewModel?.setDrawerOpenState() }
                    )
                },
                bottomBar = {
                    if (state.value.appbarState.bottomVarVisible)
                        when (state.value.currentPageState.page) {
                            LandingPageViewModel.TopLevelPage.NEWS -> {
                                NewsBottomNavigation(
                                    selectedLabel = state.value.currentPageState.currentTab.name,
                                    onTabSelected = {
                                        landingPageViewModel?.pushCurrentState(
                                            it.destination
                                        )
                                        landingPageViewModel?.setActiveTab(it)
                                    },
                                    onNavigate = {
                                        navController.navigate(it) {
                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }

                            else -> {}
                        }
                },
            ) {
                NavHost(
                    modifier = Modifier.padding(it),
                    navController = navController,
                    startDestination = NEWS_HOME_PAGE
                ) {

                    //News
                    composable(NEWS_HOME_PAGE) {
                        landingPageViewModel?.setAppBarState(
                            displayingTopBar = true,
                            displayingBottomBar = true
                        )
                        NewsHomePage(navController, homePageViewModel, landingPageViewModel)
                    }
                    composable(ALERT_PAGE) {
                        landingPageViewModel?.setAppBarState(
                            displayingTopBar = true,
                            displayingBottomBar = true
                        )
                        AlertPage()
                    }
                    composable(PROFILE_PAGE) {
                        landingPageViewModel?.setAppBarState(
                            displayingTopBar = true,
                            displayingBottomBar = true
                        )
                        ProfilePage()
                    }
                    composable(SHOPPING_PAGE) {
                        landingPageViewModel?.setAppBarState(
                            displayingTopBar = true,
                            displayingBottomBar = true
                        )
                        ShoppingPage()
                    }
                    composable(ARTICLE_PAGE_PATH) {
                        landingPageViewModel?.setAppBarState(
                            displayingTopBar = false,
                            displayingBottomBar = false
                        )
                        val articleId: Long =
                            it.arguments?.get(ARTICLE_PATH).toString().toLong()
                        ArticlePage(articleId, articlePageViewModel) { onBackPressed() }
                        argument(ARTICLE_PATH) {
                            type = NavType.IntType
                        }
                    }

                    // Movie
                    composable(MOVIE_HOME_PAGE) {
                        landingPageViewModel?.setAppBarState(
                            displayingTopBar = true,
                            displayingBottomBar = false
                        )
                        MovieHomePage()
                    }

                    // Music
                    composable(MUSIC_HOME_PAGE) {
                        landingPageViewModel?.setAppBarState(
                            displayingTopBar = true,
                            displayingBottomBar = false
                        )
                        MusicHomePage()
                    }

                    // Photos
                    composable(PHOTO_HOME_PAGE) {
                        landingPageViewModel?.setAppBarState(
                            displayingTopBar = true,
                            displayingBottomBar = false
                        )
                        PhotoHomePage()
                    }

                    // Settings
                    composable(SETTINGS_PAGE) {
                        landingPageViewModel?.setAppBarState(
                            displayingTopBar = true,
                            displayingBottomBar = false
                        )
                        SettingsPage()
                    }
                }
            }
            if (state.value.appbarState.bottomVarVisible)
                FabButton(
                    modifier = Modifier
                        .padding(32.dp, 0.dp, 32.dp, 32.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Toast.makeText(context, "Clicked on FAB", Toast.LENGTH_SHORT).show()
                }
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
fun AppBar(
    modifier: Modifier = Modifier,
    searchText: String,
    visible: Boolean,
    onTextChange: (String) -> Unit,
    onVisibilityChange: (Boolean) -> Unit,
    onSearch: (String) -> Unit,
    onDrawerOpen: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        ImageButton(
            modifier = Modifier
                .padding(8.dp, 24.dp, 0.dp, 0.dp)
                .align(Alignment.CenterStart),
            size = 32,
            painter = painterResource(id = R.drawable.ic_nav_drawer),
            description = "Nav Drawer"
        ) {
            onDrawerOpen()
        }
        SearchTextField(
            modifier = Modifier
                .fillMaxWidth(.6f)
                .align(Alignment.CenterEnd)
                .padding(8.dp, 24.dp, 0.dp, 0.dp),
            searchText = searchText,
            visible = visible,
            onTextChange = onTextChange,
            onVisibilityChange = onVisibilityChange,
            onSearch = onSearch
        )
    }
}

@Composable
fun NewsBottomNavigation(
    selectedLabel: String,
    onTabSelected: (LandingPageViewModel.BottomTab) -> Unit,
    onNavigate: (String) -> Unit
) {
    BottomNavigation(
        selectedLabel = selectedLabel,
        items = listOf(
            TabItem.ImageTabItem(
                label = LandingPageViewModel.BottomTab.NEWS_HOME.name,
                painter = painterResource(id = R.drawable.ic_home),
            ) {
                onTabSelected(LandingPageViewModel.BottomTab.NEWS_HOME)
                onNavigate(NEWS_HOME_PAGE)
            },
            TabItem.ImageTabItem(
                label = LandingPageViewModel.BottomTab.NEWS_SHOPPING.name,
                painter = painterResource(id = R.drawable.ic_shopping),
            ) {
                onTabSelected(LandingPageViewModel.BottomTab.NEWS_SHOPPING)
                onNavigate(SHOPPING_PAGE)
            },
            TabItem.ImageTabItem(
                label = LandingPageViewModel.BottomTab.NEWS_ALERT.name,
                painter = painterResource(id = R.drawable.ic_alert),
            ) {
                onTabSelected(LandingPageViewModel.BottomTab.NEWS_ALERT)
                onNavigate(ALERT_PAGE)
            },
            TabItem.ImageTabItem(
                label = LandingPageViewModel.BottomTab.NEWS_PROFILE.name,
                painter = painterResource(id = R.drawable.ic_profile),
            ) {
                onTabSelected(LandingPageViewModel.BottomTab.NEWS_PROFILE)
                onNavigate(PROFILE_PAGE)
            }
        )
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PreviewLandingPage() {
    LandingPage()
}