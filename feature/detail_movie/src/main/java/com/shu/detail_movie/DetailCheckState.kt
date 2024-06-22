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
import com.shu.detail_movie.state.ErrorScreen
import com.shu.detail_movie.state.LoadingScreen
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
    onAllClick: (Int?) -> Unit,
) {
    val viewState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
       viewModel.getDetailUi(filmId)
    }

    when (viewState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> {
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
                    with(viewState as UiState.Success) {
                        DetailScreen(
                            movie = result.film,
                            actors = if (result.actorFilm.size < 20) result.actorFilm else result.actorFilm.take(
                                20
                            ),
                            gallery = result.gallery,
                            similar = result.similarsFilm,
                            onMovieClick = onMovieClick,
                            onGalleryClick = {},
                            onActorClick = onActorClick,
                            onMessageSent = {
                                onMessageSent(
                                    result.film
                                )

                            },
                            onAllClick = onAllClick
                        )
                    }
                }
            )
        }

        is UiState.Error -> ErrorScreen(
            message = (viewState as UiState.Error).message,
            retryAction = { navController.navigateUp() },
        )

    }
}
/*@Composable
private fun getState(detailViewModel: DetailViewModel, filmId: Int): State<UiState> {
    return produceState<UiState>(initialValue = UiState.Loading) {
        value = detailViewModel.getFilm(filmId)
    }
}*/
