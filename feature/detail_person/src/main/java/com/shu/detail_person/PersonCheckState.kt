package com.shu.detail_person

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Scaffold
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
    personId: Int,
    viewModel: PersonViewModel = hiltViewModel(),
    navController: NavHostController,
    onMovieClick: (Int?) -> Unit
) {
    val viewState: State<UiState> = getState(viewModel, personId)
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
                    PersonScreen(
                       person = viewStateResult.person,
                        onMovieClick = onMovieClick,
                    )
                }
                is UiState.Error -> ErrorScreen(
                    retryAction = { },
                )
            }
        })
}

@Composable
private fun getState(personViewModel: PersonViewModel, personId: Int): State<UiState> {
    return produceState<UiState>(initialValue = UiState.Loading) {
        value = personViewModel.getInfo(personId)
    }
}
