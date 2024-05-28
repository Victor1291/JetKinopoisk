package com.shu.detail_movie

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shu.detail_movie.state.ErrorScreen
import com.shu.detail_movie.state.LoadingScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCheckState(
    filmId: Int,
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavHostController,
    onMovieClick: (Int?) -> Unit
) {
    val viewState: State<UiState> = getState(viewModel, filmId)
    Scaffold(
        topBar = {
            TopBar(
                header = "",
                leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                onLeftIconClick = { navController.navigateUp() },
            )
        },
        content = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            when (val viewStateResult = viewState.value) {
                is UiState.Loading -> LoadingScreen()
                is UiState.Success -> {
                    DetailScreen(
                        movie = viewStateResult.movie,
                     //   modifier = modifier,
                        onMovieClick = onMovieClick
                    )
                }

                is UiState.Error -> ErrorScreen(
                    retryAction = { },
                )
            }
        })
}

@Composable
private fun getState(detailViewModel: DetailViewModel, filmId: Int): State<UiState> {
    return produceState<UiState>(initialValue = UiState.Loading) {
        value = detailViewModel.getFilm(filmId)
    }
}
