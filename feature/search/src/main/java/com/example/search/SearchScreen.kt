package com.example.search

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.design_system.component.MovieItemCard
import com.example.search.components.MaterialSearch
import com.example.search.components.PersonItemCard
import com.shu.models.CinemaItem
import com.shu.models.FilmVip
import com.shu.models.detail_person.SearchPerson
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    innerPadding: PaddingValues,
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
    val isRefresh = remember { mutableStateOf(false) }
    val lazyPagingItems =
        if (isSearchPersonActive.value) {
            searchViewModel.pagedMovies.collectAsLazyPagingItems()
        }  else {
            searchViewModel.pagedPerson.collectAsLazyPagingItems()
        }



    val swipeRefreshState =
        rememberPullRefreshState(false, onRefresh = { lazyPagingItems.refresh() })

    val state = rememberLazyGridState()

    //scroll Material Search

    val topBarHeight = 70.dp
    val topBarHeightPx = with(LocalDensity.current) { topBarHeight.roundToPx().toFloat() }
    val topBarOffsetHeightPx = remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset, source: NestedScrollSource
            ): Offset {

                val delta = available.y
                //Log.d("delta", " $delta")
                val newOffset =
                    topBarOffsetHeightPx.floatValue - delta //меняем направление + или -
                topBarOffsetHeightPx.floatValue = newOffset.coerceIn(0f, topBarHeightPx)

                return Offset.Zero
            }
        }
    }
    val select = remember {
        mutableStateOf(false)
    }

    if (filter != null) {
        if (
            searchTextState.value.country != filter.country
            || searchTextState.value.genres != filter.genres
            || searchTextState.value.ratingFrom != filter.ratingFrom
            || searchTextState.value.ratingTo != filter.ratingTo
            || searchTextState.value.yearFrom != filter.yearFrom
            || searchTextState.value.yearTo != filter.yearTo
            || searchTextState.value.type != filter.type
            || searchTextState.value.order != filter.order
        ) {
            Log.d("searchScreen", " 1. not null $filter ")
            //устанавливаем фильтр
            searchViewModel.setFilter(filter.copy(page = 1, keyword = ""))
            //обновляем экран
            select.value = !select.value
        }
    }


    Scaffold(modifier = Modifier.nestedScroll(nestedScrollConnection),


        topBar = {
            MaterialSearch(
                searchViewModel,
                onRefreshClick = {
                    lazyPagingItems.refresh()
                },
                onPersonClick = {
                    isSearchPersonActive.value = !isSearchPersonActive.value
                    searchViewModel.title.value.isSearchPerson =
                        !searchViewModel.title.value.isSearchPerson
                },
                isSearchPersonActive = isSearchPersonActive,
                onTuneClick = { onTuneClick(searchTextState.value) },
                modifier = Modifier
                    .height(topBarHeight)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = -topBarOffsetHeightPx.floatValue.roundToInt()
                        )
                    },
            )
        }
    ) { inner ->
        val padding = inner

        Box(
            Modifier
                .fillMaxSize()
                .pullRefresh(swipeRefreshState)
            //.padding(bottom = 90.dp)
        ) {


            LaunchedEffect(select) {
                state.scrollToItem(1)
            }

            LazyVerticalGrid(
                modifier = Modifier,
                columns = GridCells.Adaptive(150.dp),
                contentPadding = innerPadding,
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
