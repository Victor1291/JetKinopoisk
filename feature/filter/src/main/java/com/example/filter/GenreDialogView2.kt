package com.example.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.filter.components.MaterialSearch
import com.shu.models.Genres

@Composable
fun GenreDialogView2(
    onDismissRequest: () -> Unit,
    onConfirmation: (Genres) -> Unit,
    viewModel: FilterViewModel,
    genreSelected: Genres,
    onDismiss: () -> Unit
) {

    val filter = viewModel.filter.collectAsState()
    val city by viewModel.genres.collectAsState()
    LaunchedEffect(true) {
        viewModel.updateSearchTextState("")
        viewModel.refreshGenres()
    }

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(genreSelected) }

    Column(
        Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        MaterialSearch(
            viewModel = viewModel,
            isCountries = false,
            city = emptyList(),
            genres = city,
            onDismiss = { onDismiss() },
            onConfirmation = { _, genre ->
                onConfirmation(genre)
            },
        )

        LazyColumn(
            Modifier
                .selectableGroup()
                .fillMaxWidth()
                .height(300.dp), //TODO сделать выбор высоты от ориентации экрана
        ) {
            items(city.size) { item ->

                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .selectable(
                            selected = (city[item].genre == selectedOption.genre),
                            onClick = { onOptionSelected(city[item]) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (city[item].genre == selectedOption.genre),
                        onClick = null // null recommended for accessibility with screenreaders
                    )
                    Text(
                        text = city[item].genre,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            TextButton(
                onClick = { onDismissRequest() },
                modifier = Modifier.padding(8.dp),
            ) {
                Text("Dismiss")
            }
            TextButton(
                onClick = { onConfirmation(selectedOption) },
                modifier = Modifier.padding(8.dp),
            ) {
                Text("Confirm")
            }
        }
    }
}