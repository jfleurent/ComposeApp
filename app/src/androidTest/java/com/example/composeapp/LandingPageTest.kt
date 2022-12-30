package com.example.composeapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.composeapp.ui.view.MainActivity
import com.example.composeapp.ui.view.components.DRAWER_ITEM_PREFIX_TAG
import com.example.composeapp.ui.view.page.LANDING_PAGE_NAV_BUTTON_TAG
import com.example.composeapp.ui.view.page.movies.MOVIE_HOME_PAGE
import com.example.composeapp.ui.view.page.music.MUSIC_HOME_PAGE
import com.example.composeapp.ui.view.page.news.NEWS_HOME_PAGE
import com.example.composeapp.ui.view.page.photos.PHOTO_HOME_PAGE
import com.example.composeapp.ui.view.page.settings.SETTINGS_PAGE
import com.example.composeapp.viewmodel.LandingPageViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LandingPageTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Before
    fun setUp(){

    }

    @Test
    fun landingPageContentTest(){

    }

    @Test
    fun landingPageSearchButtonClickTest(){
        composeTestRule
            .onNodeWithContentDescription("Search")
            .performClick()
        composeTestRule
            .onNodeWithText("Search")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun landingPageNavDrawerButtonClickTest(){
        composeTestRule
            .onNodeWithTag(LANDING_PAGE_NAV_BUTTON_TAG)
            .performClick()
        composeTestRule
            .onNodeWithTag("${DRAWER_ITEM_PREFIX_TAG}_${NEWS_HOME_PAGE}")
            .assertExists()
            .assertIsDisplayed()
             .assertIsSelected()
        composeTestRule
            .onNodeWithTag("${DRAWER_ITEM_PREFIX_TAG}_${MOVIE_HOME_PAGE}")
            .assertExists()
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("${DRAWER_ITEM_PREFIX_TAG}_${PHOTO_HOME_PAGE}")
            .assertExists()
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("${DRAWER_ITEM_PREFIX_TAG}_${MUSIC_HOME_PAGE}")
            .assertExists()
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithTag("${DRAWER_ITEM_PREFIX_TAG}_${SETTINGS_PAGE}")
            .assertExists()
            .assertIsDisplayed()
    }

}