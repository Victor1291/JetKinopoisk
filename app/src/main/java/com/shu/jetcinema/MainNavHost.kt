package com.shu.jetcinema

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.example.bottom_sheet.BottomSheetScreen
import com.example.bottom_sheet.InputDialogView
import com.example.filter.CountryDialogView
import com.example.filter.FilterSearch
import com.example.gallery.GalleryState
import com.example.my_list.ListMovieInCollection
import com.example.profile.ProfileScreen
import com.example.search.SearchScreen
import com.shu.detail_movie.DetailCheckState
import com.shu.detail_person.PersonCheckState
import com.shu.home.MainCheckState
import com.shu.list_movies.ListScreen
import com.shu.models.details.DetailMovie


private const val argumentKey = "arg"
private const val myKey = "my"
private const val filterKey = "filter"
private const val personKey = "person"
private const val key = "data"

data class Sheet(
    var isShow: Boolean = false,
    var film: DetailMovie? = null
)

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier,
    viewModel: MainViewModel,
    sheetState: SheetState,
    innerPadding: PaddingValues,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {

    var showBottomSheet by remember {
        mutableStateOf(Sheet())
    }


    val context = LocalContext.current.applicationContext

    NavHost(
        navController = navController,
        startDestination = NavigationScreens.MainScreen.route
    ) {
        composable(NavigationScreens.MainScreen.route) {
            MainCheckState(
                innerPadding = innerPadding,
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

        //ProfileScreen
        composable(NavigationScreens.ProfileScreen.route) {
            ProfileScreen(
                modifier = modifier,
                innerPadding = innerPadding,
                onMovieClick = { filmId ->
                    navController.navigate(
                        route = "${NavigationScreens.DetailScreen.route}/${filmId}"
                    )
                },
                onCreateClick = {
                    val filmId = 0
                    navController.navigate(
                        route = "${NavigationScreens.BottomDialog.route}/${filmId}"
                    )
                },
                onAllClick = { name ->
                    navController.navigate(
                        route = "${NavigationScreens.MyListScreen.route}/${name}"
                    )
                },
            )
        }

        //My_List
        composable(
            route = "${NavigationScreens.MyListScreen.route}/{$myKey}",
            arguments = listOf(navArgument(myKey) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(myKey)?.let { name ->

                ListMovieInCollection(
                    modifier = modifier,
                    innerPadding = innerPadding,
                    name = name,
                    onMovieClick = { filmId ->
                        navController.navigate(
                            route = "${NavigationScreens.DetailScreen.route}/${filmId}"
                        )
                    },
                    navController = navController
                )
            }
        }


        //PersonScreen
        composable(
            route = "${NavigationScreens.PersonScreen.route}/{$personKey}",
            arguments = listOf(navArgument(personKey) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(personKey)?.let { personId ->
                PersonCheckState(
                    modifier = modifier,
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
        //DetailScreen
        composable(
            route = "${NavigationScreens.DetailScreen.route}/{$argumentKey}",
            arguments = listOf(navArgument(argumentKey) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(argumentKey)?.let { kinopoiskId ->

                DetailCheckState(
                    modifier = modifier,
                    innerPadding = innerPadding,
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
                    },
                    onAllClick = { filmId ->
                        navController.navigate(
                            route = "${NavigationScreens.GalleryScreen.route}/${filmId}"
                        )
                    }

                )
            }
            BackHandler {
                navController.popBackStack()
            }
        }
        //GalleryScreen
        composable(
            route = "${NavigationScreens.GalleryScreen.route}/{$argumentKey}",
            arguments = listOf(navArgument(argumentKey) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(argumentKey)?.let { kinopoiskId ->
                //TODO changeStateTOpBar
                viewModel.changeStateTOpBar(false)
                GalleryState(
                    innerPadding = innerPadding,
                    modifier = modifier,
                    filmId = kinopoiskId,
                    onBackClick = { navController.popBackStack() },
                )
            }
            BackHandler {
                navController.popBackStack()
            }
        }

        //SearchScreen
        composable(
            route = NavigationScreens.SearchScreen.route
        ) { navBackStackEntry ->
            val text = navBackStackEntry.savedStateHandle.get<String>(key)
            val paramsData = text?.let {
                FilmsParametersType.parseValue(it)
            }
            Log.i("seaBundle", " $paramsData")
            SearchScreen(
                modifier = modifier,
                innerPadding = innerPadding,
                filter = paramsData,
                onMovieClick = { filmId ->
                    viewModel.changeStateTOpBar(false)
                    navController.navigate(
                        route = "${NavigationScreens.DetailScreen.route}/${filmId}"
                    )
                },
                onActorClick = { personId ->
                    navController.navigate(
                        route = "${NavigationScreens.PersonScreen.route}/${personId}"
                    )
                },
                onTuneClick = { vip ->
                    val filmsLink = "${NavigationScreens.FilterScreen.route}/${
                        FilmsParametersType.serializeAsValue(vip)
                    }"
                    navController.navigate(
                        route = filmsLink
                    )
                }
            )


            BackHandler {
                navController.popBackStack()
            }
        }
        //FilterScreen
        composable(
            route = "${NavigationScreens.FilterScreen.route}/{$filterKey}",
            arguments = listOf(navArgument(filterKey) {
                type = FilmsParametersType
            })
        ) { navBackStackEntry ->

            val arguments = navBackStackEntry.arguments
            val params = arguments?.getString(filterKey)

            val paramsData = params?.let {
                FilmsParametersType.parseValue(it)
            }
            Log.e("seaFilter", "  $paramsData")
            if (paramsData != null) {
                FilterSearch(
                    modifier = modifier,
                    filmVip = paramsData,
                    onBackClick = { vip ->
                        Log.e("seaFilter", " save $vip")
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set(key, FilmsParametersType.serializeAsValue(vip))
                        navController.popBackStack()
                    },
                    onSelectYear = {

                    }
                )
            }

            BackHandler {
                navController.popBackStack()
            }
        }
        //ListMovies
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
                modifier = modifier,
                innerPadding = innerPadding,
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
            backStackEntry.arguments?.getInt(argumentKey)?.let {
                InputDialogView(
                    onShowSnackbar = onShowSnackbar,
                    onDismiss = {
                        navController.popBackStack()
                    }
                )
            }
            BackHandler {
                navController.popBackStack()
            }
        }

        dialog(
            route = "${NavigationScreens.YearDialog.route}/{$argumentKey}",
            arguments = listOf(navArgument(argumentKey) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt(argumentKey)?.let {
                InputDialogView(
                    onShowSnackbar = onShowSnackbar,
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
    //TODO добавить флаг сокрытия панели нижней навигации и топ бара.
}


