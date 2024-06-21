package com.shu.jetcinema

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.example.bottom_sheet.BottomSheetScreen
import com.example.bottom_sheet.InputDialogView
import com.example.search.SearchScreen
import com.shu.detail_movie.DetailCheckState
import com.shu.detail_person.PersonCheckState
import com.shu.home.CheckState
import com.shu.list_movies.ListScreen
import com.shu.models.details.DetailMovie


private const val argumentKey = "arg"
private const val personKey = "person"

data class Sheet(
    var isShow: Boolean = false,
    var film: DetailMovie? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
    sheetState: SheetState,
) {

    var showBottomSheet by remember {
        mutableStateOf(Sheet())
    }
    val context = LocalContext.current.applicationContext

    NavHost(
        navController = navController, startDestination = NavigationScreens.MainScreen.route
    ) {
        composable(NavigationScreens.MainScreen.route) {
            viewModel.changeStateTOpBar(true)
            CheckState(
                onMovieClick = { filmId ->
                    navController.navigate(
                        route = "${NavigationScreens.DetailScreen.route}/${filmId}"
                    )
                },
                onPostClick = {},
                onListClick = { vip ->
                    val filmsLink = "${NavigationScreens.ListMovies.route}/${
                        FilmsParametersType.serializeAsValue(vip)
                    }"
                    navController.navigate(
                        route = filmsLink
                    )
                }
            )
        }

        composable(NavigationScreens.SearchScreen.route) {
            SearchScreen(
                onMovieClick = { filmId ->
                    viewModel.changeStateTOpBar(false)
                    navController.navigate(
                        route = "${NavigationScreens.DetailScreen.route}/${filmId}"
                    )
                })
            BackHandler {
                navController.popBackStack()
            }
        }

        composable(
            route = "${NavigationScreens.PersonScreen.route}/{$personKey}",
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
                            route = "${NavigationScreens.DetailScreen.route}/${filmId}"
                        )
                    }
                )

            }
            BackHandler {
                navController.popBackStack()
            }
        }
        composable(
            route = "${NavigationScreens.DetailScreen.route}/{$argumentKey}",
            arguments = listOf(navArgument(argumentKey) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(argumentKey)?.let { kinopoiskId ->
                //TODO changeStateTOpBar
                viewModel.changeStateTOpBar(false)
                DetailCheckState(
                    onMovieClick = { filmId ->
                        navController.navigate(
                            route = "${NavigationScreens.DetailScreen.route}/${filmId}"
                        )
                    },
                    navController = navController,
                    filmId = kinopoiskId,
                    onActorClick = { personId ->
                        navController.navigate(
                            route = "${NavigationScreens.PersonScreen.route}/${personId}"
                        )
                    },
                    onMessageSent = { film ->
                        showBottomSheet = Sheet(
                            isShow = true,
                            film = film
                        )
                        /*  navController.navigate(
                          route = "${BottomNavigationScreens.BottomDialog.route}/${filmId}"
                      )*/
                    }

                )
            }
            BackHandler {
                navController.popBackStack()
            }
        }

        composable(
            route = "${NavigationScreens.ListMovies.route}/{$argumentKey}",
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
                        route = "${NavigationScreens.DetailScreen.route}/${filmId}"
                    )
                })

            BackHandler {
                navController.popBackStack()
            }
        }
        //  bottomSheet("my_dialog") { MyDialogComposable() }

        dialog(
            route = "${NavigationScreens.BottomDialog.route}/{$argumentKey}",
            arguments = listOf(navArgument(argumentKey) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(argumentKey)?.let { kinopoiskId ->
                InputDialogView(
                    onDismiss = {
                        navController.popBackStack()
                    }
                )
            }
            BackHandler {
                navController.popBackStack()
            }
        }
    }
    if (showBottomSheet.isShow) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = showBottomSheet.copy(isShow = false) },
            sheetState = sheetState
        ) {
            showBottomSheet.film?.let { filmId ->
                BottomSheetScreen(
                    film = filmId,
                    onCreateClick = {
                       // showBottomSheet = showBottomSheet.copy(isShow = false)
                        navController.navigate(
                            route = "${NavigationScreens.BottomDialog.route}/${filmId.kinopoiskId}"
                        )

                        Toast.makeText(context, "Create", Toast.LENGTH_LONG).show()
//                    navController.navigate() {
//          popUpTo(0)
//                    }
                    })
            }

        }
    }
}


