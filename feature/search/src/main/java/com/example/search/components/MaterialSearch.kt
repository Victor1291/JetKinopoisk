package com.example.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.search.ListViewModel
import com.example.search.components.Utils.originUsersList
import com.shu.models.FilmVip
import com.shu.mylibrary.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialSearch(
    viewModel: ListViewModel,
    searchTextState: State<FilmVip>,
    onRefreshClick: () -> Unit,
    onPersonClick: () -> Unit,
    onTuneClick: () -> Unit,
    isSearchPersonActive : MutableState<Boolean>
) {
    val isActive = remember {
        mutableStateOf(false)
    }
    val mainList = remember {
        mutableStateOf(originUsersList)
    }
    SearchBar(modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(R.dimen.padding_medium)),
        query = searchTextState.value.keyword,
        onQueryChange = { text ->
            viewModel.setTitle(FilmVip(keyword = text))
            onRefreshClick()
            // mainList.value = Utils.search(text, originUsersList)
        },
        onSearch = { text ->
            viewModel.setTitle(FilmVip(keyword = text))
            onRefreshClick()
            isActive.value = false
        },
        placeholder = {
            Text(text = if (isSearchPersonActive.value) stringResource(R.string.search_movie) else stringResource(
                R.string.search_person))
        },
        active = isActive.value,
        onActiveChange = {
            isActive.value = it
        },
        leadingIcon = {
                Icon(
                    if (isSearchPersonActive.value) Icons.Default.Person else Icons.Default.PlayArrow,
                    tint = Color.Black,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(48.dp)
                        .clickable { onPersonClick() },
                    contentDescription = stringResource(R.string.personsearch)
                )
        },
        trailingIcon = {
            Icon(
                Icons.Default.Build,
                tint = Color.Black,
                modifier = Modifier
                    .padding(8.dp)
                    .size(56.dp)
                    .clickable { onTuneClick() },
                contentDescription = stringResource(R.string.tuning)
            )
        }
    ) {
        LazyColumn {
            items(mainList.value.size) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .clickable {
                            viewModel.setTitle(FilmVip(keyword = mainList.value[item]))
                            onRefreshClick()
                            isActive.value = false
                        }, contentAlignment = Alignment.Center
                ) {
                    Text(text = mainList.value[item])
                }
            }
        }
    }
}

object Utils {
    val originUsersList = listOf(
        "doom",
        "super",
        "virus",
        "cat",
        "dog",
        "mouse",
    )

    fun search(text: String, city: List<String>): List<String> {
        return city.filter {
            it.lowercase().startsWith(text.lowercase())
        }
    }
}