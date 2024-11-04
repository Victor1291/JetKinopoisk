package com.shu.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design_system.state.ErrorScreen
import com.example.design_system.state.LoadingScreen
import com.shu.models.FilmVip

@Composable
fun MainCheckState(
    innerPadding: PaddingValues,
    viewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (Int?) -> Unit,
    onPostClick: (Int?) -> Unit,
    onListClick: (FilmVip?) -> Unit,
) {
    val viewState by viewModel.uiState.collectAsState()

    when (viewState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> {
            with(viewState as UiState.Success) {
                HomeScreen(
                    innerPadding = innerPadding,
                    manyScreens = manyScreens,
                    posts = posts,
                    onPostClick = onPostClick,
                    onMovieClick = onMovieClick,
                    onListClick = onListClick,
                    viewModel = viewModel
                )
            }
        }

        is UiState.Error -> ErrorScreen(
            message = (viewState as UiState.Error).message,
            retryAction = { viewModel.retry() },
        )
    }
}
