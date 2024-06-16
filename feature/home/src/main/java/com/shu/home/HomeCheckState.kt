package com.shu.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import com.shu.home.state.ErrorScreen
import com.shu.home.state.LoadingScreen
import com.shu.models.FilmVip

@Composable
fun CheckState(
    viewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (Int?) -> Unit,
    onListClick: (FilmVip?) -> Unit,
) {
    val viewState: State<UiState> = getState(viewModel)

    when (val viewStateResult = viewState.value) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> {
            HomeScreen(
                manyScreens = viewStateResult.manyScreens,
                posts = viewStateResult.posts,
                onMovieClick = onMovieClick,
                onListClick = onListClick
            )
        }

        is UiState.Error -> ErrorScreen(
            retryAction = { },
        )
    }
}

@Composable
private fun getState(viewModel: HomeViewModel): State<UiState> {
    return produceState<UiState>(initialValue = UiState.Loading) {
        value = viewModel.getInfo()
    }
}
