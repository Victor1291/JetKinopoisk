package com.shu.home

import android.util.Log
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
           /*  Log.d("success", "movie in state ${(viewState as UiState.Success).manyScreens.homeListScreen.size}")
             Log.d("success", "size list first in state ${(viewState as UiState.Success).manyScreens.homeListScreen.first().size}")
             Log.d("success", "size list second in state ${(viewState as UiState.Success).manyScreens.homeListScreen[1].size}")
             Log.d("success", "size list third in state ${(viewState as UiState.Success).manyScreens.homeListScreen[2].size}")
            *//* WeatherForecast(
                 weather = (viewState as UiState.Success).weather,

             )*/

            MovieScreen(manyScreens = (viewState as UiState.Success).manyScreens)
        }

        is UiState.Error -> ErrorScreen(
            retryAction = {  },
        )

    }
}
