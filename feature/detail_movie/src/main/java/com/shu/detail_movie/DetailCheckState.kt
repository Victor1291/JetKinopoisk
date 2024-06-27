package com.shu.detail_movie

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shu.detail_movie.state.ErrorScreen
import com.shu.detail_movie.state.LoadingScreen
import com.shu.models.details.DetailMovie

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
    val context: Context = LocalContext.current.applicationContext
    val uriHandler = LocalUriHandler.current
    LaunchedEffect(key1 = true) {
        viewModel.getDetailUi(filmId)
        viewModel.filmId = filmId
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
                    with(viewState as UiState.Success) {
                        DetailScreen(
                            modifier = Modifier
                                .padding(innerPadding),
                            viewModel = viewModel,
                            movie = result.film,
                            actors = if (result.actorFilm.size < 20) result.actorFilm else result.actorFilm.take(
                                20
                            ),
                            gallery = result.gallery,
                            similar = result.similarsFilm,
                            onMovieClick = onMovieClick,
                            onGalleryClick = {},
                            onActorClick = onActorClick,
                            onCollectionClick = {
                                onMessageSent(
                                    result.film
                                )

                            },
                            onShareClick = { imdb ->

                                val shareIntent = Intent(Intent.ACTION_SEND)
                                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                shareIntent.setType("text/plain")
                                shareIntent.putExtra(Intent.EXTRA_TEXT, imdb)
                                val newIntent = Intent.createChooser(shareIntent, "ShareWith")
                                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                                ContextCompat.startActivity(
                                    context,
                                    newIntent,
                                    null
                                )
                            },
                            onBrowsingClick = { webUrl ->
                                uriHandler.openUri(webUrl)
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
