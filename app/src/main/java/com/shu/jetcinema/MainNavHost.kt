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
import com.shu.list_movies.ListScreen


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
            CheckState(
                onMovieClick = { filmId ->
                    navController.navigate(
                        route = "${BottomNavigationScreens.DetailScreen.route}/${filmId}"
                    )
                },
                onListClick = { vip ->
                    val filmsLink = "${BottomNavigationScreens.ListMovies.route}/${
                        FilmsParametersType.serializeAsValue(vip)
                    }"
                    navController.navigate(
                        route = filmsLink
                    )
                }
            )
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
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(argumentKey)?.let { filmId ->
                viewModel.changeStateTOpBar(false)
                DetailCheckState(onMovieClick = { filmId ->
                    navController.navigate(
                        route = "${BottomNavigationScreens.DetailScreen.route}/${filmId}"
                    )
                }, navController = navController, filmId = filmId)
            }
            BackHandler {
                navController.popBackStack()
            }
        }

        composable(
            route = "${BottomNavigationScreens.ListMovies.route}/{$argumentKey}",
            arguments = listOf(navArgument(argumentKey) {
                type = FilmsParametersType
            })
        ) { navBackStackEntry ->

            val arguments = navBackStackEntry.arguments
            val params = arguments?.getString(argumentKey)

            val paramsData = params?.let {
                FilmsParametersType.parseValue(it)
            }
            ListScreen(
                filmVip = paramsData,
                navController = navController,
                onMovieClick = { filmId ->
                    viewModel.changeStateTOpBar(false)
                    navController.navigate(
                        route = "${BottomNavigationScreens.DetailScreen.route}/${filmId}"
                    )
                })

            BackHandler {
                navController.popBackStack()
            }
        }

    }
}
