package com.shu.detail_person

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.design_system.state.ErrorScreen
import com.example.design_system.state.LoadingScreen

@Composable
fun PersonCheckState(
    modifier: Modifier,
    personId: Int,
    navController: NavHostController,
    onMovieClick: (Int?) -> Unit
) {
    val viewModel = hiltViewModel<PersonViewModel, PersonViewModel.Factory> { factory ->
        factory.create(personId)
    }

    val viewState by viewModel.uiState.collectAsState()

    when (viewState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> {
            PersonScreen(
                modifier = modifier,
                person = (viewState as UiState.Success).person,
                onMovieClick = onMovieClick,
                onLeftClick = { navController.popBackStack() },
            )
        }

        is UiState.Error -> ErrorScreen(
            message = (viewState as UiState.Error).message,
            retryAction = { navController.navigateUp() },
        )
    }
}


//@Composable
//private fun getState(personViewModel: PersonViewModel, personId: Int): State<UiState> {
//    return produceState<UiState>(initialValue = UiState.Loading) {
//        value = personViewModel.getInfo(personId)
//    }
//}
