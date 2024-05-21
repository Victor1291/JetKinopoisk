package com.shu.detail_movie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import com.shu.detail_movie.state.ErrorScreen
import com.shu.detail_movie.state.LoadingScreen


@Composable
fun DetailCheckState(
    filmId : Int,
    viewModel: DetailViewModel = hiltViewModel(),
    onMovieClick: (Int?) -> Unit
) {
    val viewState: State<UiState> = getState(viewModel, filmId)


    when (val viewStateResult = viewState.value) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> {
            DetailScreen(movie = viewStateResult.movie,onMovieClick = onMovieClick)
        }

        is UiState.Error -> ErrorScreen(
            retryAction = {  },
        )

    }
}
@Composable
private fun getState(detailViewModel: DetailViewModel, filmId: Int): State<UiState> {
    return produceState<UiState>(initialValue = UiState.Loading) {
        value = detailViewModel.getFilm(filmId)
    }
}
