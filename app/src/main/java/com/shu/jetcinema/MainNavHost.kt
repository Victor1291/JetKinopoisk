package com.shu.jetcinema

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.search.SearchScreen
import com.shu.detail_movie.DetailCheckState
import com.shu.detail_person.PersonCheckState
import com.shu.home.CheckState
import com.shu.list_movies.ListScreen


private const val argumentKey = "arg"
private const val personKey = "person"

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
                onPostClick = {},
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
            SearchScreen(
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

        composable(
            route = "${BottomNavigationScreens.PersonScreen.route}/{$personKey}",
            arguments = listOf(navArgument(personKey) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(personKey)?.let { personId ->
                PersonCheckState(
                    personId = personId,
                    navController = navController,
                    onMovieClick = { filmId ->
                        navController.navigate(
                            route = "${BottomNavigationScreens.DetailScreen.route}/${filmId}"
                        )
                    }
                )

            }
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
            backStackEntry.arguments?.getInt(argumentKey)?.let { kinopoiskId ->
                viewModel.changeStateTOpBar(false)
                DetailCheckState(
                    onMovieClick = { filmId ->
                        navController.navigate(
                            route = "${BottomNavigationScreens.DetailScreen.route}/${filmId}"
                        )
                    },
                    navController = navController,
                    filmId = kinopoiskId,
                    onActorClick = { personId ->
                        navController.navigate(
                            route = "${BottomNavigationScreens.PersonScreen.route}/${personId}"
                        )
                    }
                )
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
