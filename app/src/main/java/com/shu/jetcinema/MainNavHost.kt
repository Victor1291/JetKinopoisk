package com.shu.jetcinema

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shu.home.CheckState


@Composable
fun MainNavHost(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: MainViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationScreens.MainScreen.route
    ) {
        composable(BottomNavigationScreens.MainScreen.route) {
            viewModel.changeStateTOpBar(true)
            CheckState()
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
        composable(BottomNavigationScreens.DetailScreen.route) {
            /* viewModel.getWeather()
             DetailScreen(viewModel)*/
            BackHandler {
                navController.popBackStack()
            }
        }
    }
}
