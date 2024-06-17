package com.shu.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    val viewState by viewModel.uiState.collectAsState()

    when (viewState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> {
            HomeScreen(
                manyScreens = (viewState as UiState.Success).manyScreens,
                posts = (viewState as UiState.Success).posts,
                onMovieClick = onMovieClick,
                onListClick = onListClick
            )
        }

        is UiState.Error -> ErrorScreen(
            retryAction = { viewModel.retry() },
        )
    }
}
