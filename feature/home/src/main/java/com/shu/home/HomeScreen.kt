package com.shu.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.shu.home.state.ErrorScreen
import com.shu.home.state.LoadingScreen


@Composable
fun HomeScreen(
    viewModel: MainViewModel,
) {
    val viewState by viewModel.uiState.collectAsState()


    when (viewState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> {
            // Log.d("success", "city in state ${(viewState as UiState.Success).weather.location?.name}")
            /* WeatherForecast(
                 weather = (viewState as UiState.Success).weather,

             )*/
            MovieScreen(manyScreens = (viewState as UiState.Success).manyScreens)
        }

        is UiState.Error -> ErrorScreen(
            retryAction = {  },
        )

    }
}
