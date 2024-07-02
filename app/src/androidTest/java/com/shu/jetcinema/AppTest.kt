package com.shu.jetcinema

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AppTest {


    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun check_Loading(): Unit = with(composeTestRule) {
        onRoot().printToLog("Ui test")
        onNodeWithText("LOADING...").assertIsDisplayed()
    }
    @Test
    fun check_HomeScreen(): Unit = with(composeTestRule) {
        onRoot().printToLog("Ui test")
        onNodeWithText("LOADING...").assertIsDisplayed()
    }
    @Test
    fun app_canNavigateToAllScreens(): Unit = with(composeTestRule) {

        // Navigate to Search
        onNodeWithText("ALL").performClick().assertIsDisplayed()
    }

    @Test
    fun makeSearch_LoadMovies(): Unit = with(composeTestRule) {
        // Check app launches at HOME
        onRoot().printToLog("Ui test")
        onNodeWithText("Home").assertIsDisplayed()

        // Navigate to Search
        onNodeWithText("Search").performClick().assertIsDisplayed()


        onNodeWithText("frog").performTextInput("doom")

        waitUntil(5000) {
            onAllNodesWithTag("movie")
                .fetchSemanticsNodes().isNotEmpty()
        }

        onNodeWithText("Судный день").assertIsDisplayed()

    }

}
