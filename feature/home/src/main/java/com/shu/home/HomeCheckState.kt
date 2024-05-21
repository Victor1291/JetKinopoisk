package com.shu.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.shu.home.state.ErrorScreen
import com.shu.home.state.LoadingScreen


@Composable
fun CheckState(
    viewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (Int?) -> Unit
) {
    val viewState by viewModel.uiState.collectAsState()


    when (viewState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> {
           /*  Log.d("success", "movie in state ${(viewState as UiState.Success).manyScreens.homeListScreen.size}")
             Log.d("success", "size list first in state ${(viewState as UiState.Success).manyScreens.homeListScreen.first().size}")
             Log.d("success", "size list second in state ${(viewState as UiState.Success).manyScreens.homeListScreen[1].size}")
             Log.d("success", "size list third in state ${(viewState as UiState.Success).manyScreens.homeListScreen[2].size}")
            *//* WeatherForecast(
                 weather = (viewState as UiState.Success).weather,

             )*/

            HomeScreen(manyScreens = (viewState as UiState.Success).manyScreens,onMovieClick = onMovieClick)
        }

        is UiState.Error -> ErrorScreen(
            retryAction = {  },
        )

    }
}
