package com.example.filter.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
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
    isCountries: Boolean,
    city: List<Countries>,
    genres: List<Genres>,
    onDismiss: () -> Unit,
    onConfirmation: (Countries, Genres) -> Unit,
) {
    val isActive = remember {
        mutableStateOf(false)
    }

    val filter = viewModel.filter.collectAsState()
    val searchTextState by viewModel.searchTextState.collectAsState()
    val mainList = remember {
        mutableStateOf(city)
    }
    val mainListGenres = remember {
        mutableStateOf(genres)
    }

    SearchBar(modifier = Modifier
        .fillMaxWidth(),
        query = searchTextState,
        onQueryChange = { text ->
            viewModel.updateSearchTextState(text)
            if (isCountries)
                mainList.value = Utils.searchCity(text, city)
            else
                mainListGenres.value = Utils.searchGenre(text, genres)
        },
        onSearch = {
            viewModel.updateSearchTextState(it)
            isActive.value = false
        },
        placeholder = {
            Text(text = stringResource(R.string.enter_city))
        },
        active = isActive.value,
        onActiveChange = {
            isActive.value = it
        }) {

        if (isCountries) {
            LazyColumn {
                items(mainList.value.size) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                onConfirmation(mainList.value[item], Genres(id = 0,"триллер"))
                               /* viewModel.updateSearchTextState(mainList.value[item].country)
                                viewModel.setFilter(filter.value.copy(country = mainList.value[item].id))*/
                                isActive.value = false
                                onDismiss()
                            }, contentAlignment = Alignment.Center
                    ) {
                        Text(text = mainList.value[item].country)
                    }
                }
            }
        } else {
            LazyColumn {
                items(mainListGenres.value.size) { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                onConfirmation(Countries(0,"США"), mainListGenres.value[item])
                               /* viewModel.updateSearchTextState(mainListGenres.value[item].genre)
                                viewModel.setFilter(filter.value.copy(country = mainListGenres.value[item].id))*/
                                isActive.value = false
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

object Utils {
    val originUsersList = listOf(
        Countries(34, "Россия"),
        Countries(1, "США"),
    )

    fun searchCity(text: String, city: List<Countries>): List<Countries> {
        return city.filter {
            it.country.lowercase().startsWith(text.lowercase())
        }
    }

    fun searchGenre(text: String, city: List<Genres>): List<Genres> {
        return city.filter {
            it.genre.lowercase().startsWith(text.lowercase())
        }
    }
}