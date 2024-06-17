package com.shu.weather_main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.search.ListViewModel
import com.shu.models.FilmVip
import com.shu.mylibrary.R
import com.shu.weather_main.Utils.originUsersList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialSearch(
    viewModel: ListViewModel,
    onRefreshClick: () -> Unit
) {
    val isActive = remember {
        mutableStateOf(false)
    }
    val searchTextState = viewModel.title.collectAsState()
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
            // mainList.value = Utils.search(text, city)
        },
        onSearch = {

            isActive.value = false
        },
        placeholder = {
            Text(text = stringResource(R.string.holder))
        },
        active = isActive.value,
        onActiveChange = {
            isActive.value = it
        }) {
       /* LazyColumn {
            items(mainList.value.size) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium))
                        .clickable {
                            viewModel.setTitle(FilmVip(keyword = mainList.value[item]))
                            isActive.value = false
                        }, contentAlignment = Alignment.Center
                ) {
                    Text(text = mainList.value[item])
                }
            }
        }*/
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