package com.shu.detail_person

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shu.detail_person.state.ErrorScreen
import com.shu.detail_person.state.LoadingScreen

@Composable
fun PersonCheckState(
    modifier: Modifier,
    personId: Int,
    viewModel: PersonViewModel = hiltViewModel(),
    navController: NavHostController,
    onMovieClick: (Int?) -> Unit
) {
    val viewState: State<UiState> = getState(viewModel, personId)

    when (val viewStateResult = viewState.value) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> {
            PersonScreen(
                modifier = modifier,
                person = viewStateResult.person,
                onMovieClick = onMovieClick,
                onLeftClick = { navController.popBackStack() },
            )
        }

        is UiState.Error -> ErrorScreen(
            retryAction = { },
        )
    }
}


@Composable
private fun getState(personViewModel: PersonViewModel, personId: Int): State<UiState> {
    return produceState<UiState>(initialValue = UiState.Loading) {
        value = personViewModel.getInfo(personId)
    }
}
