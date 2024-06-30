package com.example.search

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.search.components.MaterialSearch
import com.example.search.components.MovieItemCard
import com.example.search.components.PersonItemCard
import com.shu.models.CinemaItem
import com.shu.models.FilmVip
import com.shu.models.detail_person.SearchPerson
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    filter: FilmVip?,
    searchViewModel: SearchViewModel = hiltViewModel(),
    onMovieClick: (Int?) -> Unit,
    onActorClick: (Int?) -> Unit,
    onTuneClick: (FilmVip) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val isSearchPersonActive = remember {
        mutableStateOf(searchViewModel.title.value.isSearchPerson)
    }
    val searchTextState = searchViewModel.title.collectAsState()
    LaunchedEffect(key1 = 3) {
        if (filter != null) {
            Log.d("searchScreen", "  not null $filter ")
            searchViewModel.setFilter(filter)
        }
    }

    val lazyPagingItems =
        if (isSearchPersonActive.value) searchViewModel.pagedMovies.collectAsLazyPagingItems()
        else searchViewModel.pagedPerson.collectAsLazyPagingItems()

    val swipeRefreshState =
        rememberPullRefreshState(false, onRefresh = { lazyPagingItems.refresh() })

    val state = rememberLazyGridState()
    Column(
        modifier = modifier
    ) {
        MaterialSearch(
            searchViewModel,
            searchTextState = searchTextState,
            onRefreshClick = {
                lazyPagingItems.refresh()
            },
            onPersonClick = {
                isSearchPersonActive.value = !isSearchPersonActive.value
                searchViewModel.title.value.isSearchPerson =
                    !searchViewModel.title.value.isSearchPerson
            },
            isSearchPersonActive = isSearchPersonActive,
            onTuneClick = { onTuneClick(searchTextState.value) }
        )
        Box(
            Modifier
                .fillMaxSize()
                .pullRefresh(swipeRefreshState)
            //.padding(bottom = 90.dp)
        ) {
            LazyVerticalGrid(
                modifier = Modifier,
                columns = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(4.dp),
                state = state,
            ) {

                if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
                    coroutineScope.launch {
                        state.scrollToItem(0)

                    }
                    item {
                        Text(
                            text = "Waiting for items to load from the backend",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }

                items(count = lazyPagingItems.itemCount) { index ->
                    val item = lazyPagingItems[index]
                    if (item != null) {
                        if (isSearchPersonActive.value)
                            MovieItemCard(item as CinemaItem, onMovieClick = onMovieClick)
                        else
                            PersonItemCard(item as SearchPerson, onActorClick = onActorClick)
                    }
                }

            }
            PullRefreshIndicator(
                lazyPagingItems.loadState.append == LoadState.Loading,
                swipeRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
