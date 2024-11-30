package com.example.filter.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.filter.FilterViewModel
import com.example.filter.R
import com.shu.models.Countries
import com.shu.models.Genres

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialSearch(
    viewModel: FilterViewModel,
    searchTextState: String,
    onTextChange: (String) -> Unit,
    isCountries: Boolean,
    isActive: Boolean,
    city: List<Countries>,
    genres: List<Genres>,
    onDismiss: () -> Unit,
    onChangeActive: (Boolean) -> Unit,
    onConfirmation: (Countries, Genres) -> Unit,
) {

    val filter = viewModel.filter.collectAsState()

    val mainList = remember {
        mutableStateOf(city)
    }
    val mainListGenres = remember {
        mutableStateOf(genres)
    }

    DockedSearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .focusable(false),
        query = searchTextState,
        onQueryChange = { text ->
            onTextChange(text)
            if (isCountries)
                mainList.value = Utils.searchCity(text, city)
            else
                mainListGenres.value = Utils.searchGenre(text, genres)
        },
        onSearch = {
            viewModel.updateSearchTextState(it)
        },
        placeholder = {
            Text(text = stringResource(R.string.enter_city))
        },
        enabled = isActive,
        active = isActive,
        onActiveChange = {
            onChangeActive(it)
        },
        shape = SearchBarDefaults.dockedShape,
        trailingIcon = {
            Icon(
                Icons.Rounded.Search,
                tint = Color.Black,
                modifier = Modifier
                    .padding(8.dp)
                    .size(38.dp)
                    .clickable { onChangeActive(!isActive) },
                contentDescription = stringResource(R.string.search)
            )
        }
    ) {

        if (isActive) {
            if (isCountries) {
                LazyColumn(modifier = Modifier.height(300.dp)) {
                    items(mainList.value.size) { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clickable {
                                    onConfirmation(mainList.value[item], Genres(id = 0, "триллер"))
                                    viewModel.updateSearchTextState(mainList.value[item].country)
                                    viewModel.setFilter(filter.value.copy(country = mainList.value[item].id))
                                    onDismiss()
                                }, contentAlignment = Alignment.Center
                        ) {
                            Text(text = mainList.value[item].country)
                        }
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.height(300.dp)) {
                    items(mainListGenres.value.size) { item ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clickable {
                                    onConfirmation(Countries(0, "США"), mainListGenres.value[item])
                                    viewModel.updateSearchTextState(mainListGenres.value[item].genre)
                                    viewModel.setFilter(filter.value.copy(country = mainListGenres.value[item].id))
                                    onDismiss()
                                }, contentAlignment = Alignment.Center
                        ) {
                            Text(text = mainListGenres.value[item].genre)
                        }
                    }
                }
            }
        }
    }
}

object Utils {
    val originUsersList = listOf(
        Countries(34, "Россия"),
        Countries(1, "США"),
    )

    fun searchCity(text: String, city: List<Countries>): List<Countries> {
        return if (text.isNotBlank()) {
            city.filter {
                it.country.lowercase().startsWith(text.lowercase())
            }
        } else {
            city
        }
    }

    fun searchGenre(text: String, city: List<Genres>): List<Genres> {
        return if (text.isNotBlank()) {
            city.filter {
                it.genre.lowercase().startsWith(text.lowercase())
            }
        } else {
            city
        }
    }
}