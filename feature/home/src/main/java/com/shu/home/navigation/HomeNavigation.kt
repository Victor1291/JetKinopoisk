package com.shu.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.shu.home.MainCheckState

const val HOME_ROUTE = "home_screen"

fun NavController.navigateToHome(navOptions: NavOptions, innerPadding: PaddingValues) = navigate(
    HOME_ROUTE, navOptions)

fun NavGraphBuilder.forYouScreen(innerPadding: PaddingValues,onMovieClick: (Int) -> Unit) {
    composable(
        route = HOME_ROUTE,
    ) {
        MainCheckState(
            innerPadding = innerPadding,
            onMovieClick =  {},
            onPostClick = {},
            onListClick = {}
        )
    }
}