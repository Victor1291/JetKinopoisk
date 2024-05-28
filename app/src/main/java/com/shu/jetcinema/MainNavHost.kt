package com.shu.jetcinema

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shu.detail_movie.DetailCheckState
import com.shu.home.CheckState


private const val argumentKey = "arg"

@Composable
fun MainNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
) {
    NavHost(
        navController = navController, startDestination = BottomNavigationScreens.MainScreen.route
    ) {
        composable(BottomNavigationScreens.MainScreen.route) {
            viewModel.changeStateTOpBar(true)
            CheckState(onMovieClick = { filmId ->
                navController.navigate(
                    route = "${BottomNavigationScreens.DetailScreen.route}/${filmId}"
                )
            })
        }

        composable(BottomNavigationScreens.SearchScreen.route) {
            /* viewModel.changeStateTOpBar(false)
             viewModel.getAllCity()
             CityScreen(viewModel, onCityClicked = {
                 viewModel.choiceCity = it ?: "Vladivostok"
                 navController.navigate(
                     BottomNavigationScreens.DetailScreen.route
                 )
             })*/
            BackHandler {
                navController.popBackStack()
            }
        }

        composable(BottomNavigationScreens.PersonScreen.route) {
            /* viewModel.changeStateTOpBar(false)
             viewModel.getAllCity()
             CityScreen(viewModel, onCityClicked = {
                 viewModel.choiceCity = it ?: "Vladivostok"
                 navController.navigate(
                     BottomNavigationScreens.DetailScreen.route
                 )
             })*/
            BackHandler {
                navController.popBackStack()
            }
        }
        composable(
            route = "${BottomNavigationScreens.DetailScreen.route}/{$argumentKey}",
            arguments = listOf(navArgument(argumentKey) {
                type = NavType.IntType
            })
        ) {backStackEntry ->
            backStackEntry.arguments?.getInt(argumentKey)?.let {filmId ->
                viewModel.changeStateTOpBar(false)
                DetailCheckState(onMovieClick = {}, navController = navController, filmId = filmId)
            }
            BackHandler {
                navController.popBackStack()
            }
        }
    }
}
