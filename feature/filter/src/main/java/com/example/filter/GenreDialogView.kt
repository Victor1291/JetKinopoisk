package com.example.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.filter.components.MaterialSearch

@Composable
fun GenreDialogView(
    viewModel: FilterViewModel,
    onDismiss: () -> Unit
) {

    val filter = viewModel.filter.collectAsState()
    val city by viewModel.genres.collectAsState()
    LaunchedEffect(true) {
        viewModel.updateSearchTextState("")
        viewModel.refreshGenres()
    }

    val context = LocalContext.current.applicationContext
    var nameCollection by remember {
        mutableStateOf("")
    }
    Column() {
        MaterialSearch(
            viewModel = viewModel,
            isCountries = false,
            city = emptyList(),
            genres = city,
            onDismiss = { onDismiss() }
        )

        LazyColumn {
            items(city.size) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            viewModel.updateSearchTextState(city[item].genre)
                            viewModel.setFilter(filter.value.copy(genres = city[item].id, genresName = city[item].genre))
                            onDismiss()
                        }, contentAlignment = Alignment.Center
                ) {
                    Text(text = "${city[item].id}. ${city[item].genre}")
                }
            }
        }

    }

}