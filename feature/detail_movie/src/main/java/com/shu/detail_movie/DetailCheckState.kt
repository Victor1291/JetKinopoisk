package com.shu.detail_movie

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shu.models.details.DetailMovie
import kotlinx.coroutines.launch

@Composable
fun DetailCheckState(
    filmId: Int,
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavHostController,
    onMovieClick: (Int?) -> Unit,
    onActorClick: (Int?) -> Unit,
    onMessageSent: (DetailMovie?) -> Unit,
) {
    val viewState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        launch {
            viewModel.getFilm(filmId)
        }
        launch {
            viewModel.getActorFilm(filmId)
        }
        launch {
            viewModel.getGallery(filmId)
        }
        launch {
            viewModel.getSimilarsFilm(filmId)
        }
    }
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

            DetailScreen(
                movie = viewState.film,
                actors = if (viewState.actorFilm.size < 20) viewState.actorFilm else viewState.actorFilm.take(
                    20
                ),
                gallery = viewState.gallery,
                similar = viewState.similarsFilm,
                onMovieClick = onMovieClick,
                onGalleryClick = {},
                onActorClick = onActorClick,
                onMessageSent = {
                    onMessageSent(
                        viewState.film
                    )
                },
            )
        }
    )
}

/*@Composable
private fun getState(detailViewModel: DetailViewModel, filmId: Int): State<UiState> {
    return produceState<UiState>(initialValue = UiState.Loading) {
        value = detailViewModel.getFilm(filmId)
    }
}*/
