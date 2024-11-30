package com.example.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    onDismiss: () -> Unit,
) {

    val city by viewModel.filterCG.collectAsState()

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(genreSelected) }

    Column(
        // Modifier.verticalScroll(verticalState),
        verticalArrangement = Arrangement.Center
    ) {
        var isActive by remember {
            mutableStateOf(false)
        }

        var searchTextState by remember {
            mutableStateOf("")
        }

        val onTextChange = { text: String ->
            searchTextState = text
        }

        MaterialSearch(
            viewModel = viewModel,
            searchTextState = searchTextState,
            onTextChange = onTextChange,
            isCountries = false,
            isActive = isActive,
            city = emptyList(),
            genres = city.genres,
            onDismiss = {
                isActive = false
                onDismiss()
            },
            onChangeActive = {
                isActive = it
            },
            onConfirmation = { _, genre ->
                isActive = false
                onConfirmation(genre)
            },
        )

        LazyColumn(
            Modifier
                .selectableGroup()
                .fillMaxWidth()
                .height(400.dp), //TODO сделать выбор высоты от ориентации экрана
        ) {
            items(city.genres.size) { item ->

                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .selectable(
                            selected = (city.genres[item].genre == selectedOption.genre),
                            onClick = { onOptionSelected(city.genres[item]) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (city.genres[item].genre == selectedOption.genre),
                        onClick = null // null recommended for accessibility with screenreaders
                    )
                    Text(
                        text = city.genres[item].genre,
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