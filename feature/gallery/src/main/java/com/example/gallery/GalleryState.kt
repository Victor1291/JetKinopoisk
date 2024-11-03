package com.example.gallery

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design_system.state.ErrorScreen
import com.example.design_system.state.LoadingScreen


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun GalleryState(
    innerPadding: PaddingValues,
    modifier: Modifier,
    filmId: Int,
    onBackClick: () -> Unit
) {

    val viewModel = hiltViewModel<GalleryViewModel, GalleryViewModel.Factory> { factory ->
        factory.create(filmId)
    }

   /* LaunchedEffect(key1 = true) {
        if (filmId != null) {
            viewModel.setId(filmId)
            viewModel.getFirstGallery()
        }
    }
*/
    when (viewModel.uiState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> {
            GalleryScreen(
                modifier = modifier,
                galleryList = (viewModel.uiState as UiState.Success).pager ,
                countList = (viewModel.uiState as UiState.Success).total,
                viewModel = viewModel,
                onBackClick = { onBackClick() }
            )
        }

        is UiState.Error -> ErrorScreen(
            message = (viewModel.uiState as UiState.Error).message,
            retryAction = { viewModel.retry() },
        )
    }
}
