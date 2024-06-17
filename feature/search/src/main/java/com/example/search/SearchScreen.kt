package com.example.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.shu.models.FilmVip
import com.shu.weather_main.MaterialSearch
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    listViewModel: ListViewModel = hiltViewModel(),
    onMovieClick: (Int?) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val lazyPagingItems = listViewModel.pagedMovies.collectAsLazyPagingItems()

    Scaffold(topBar = {
        MaterialSearch(listViewModel, onRefreshClick = {
            lazyPagingItems.refresh()
        })
    }, content = { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        val swipeRefreshState =
            rememberPullRefreshState(false, onRefresh = { lazyPagingItems.refresh() })

        val state = rememberLazyGridState()
        LaunchedEffect(key1 = 3) {
            listViewModel.setTitle(
                FilmVip(
                    keyword = "super"
                )
            )
        }

        Box(
            Modifier
                .fillMaxSize()
                .pullRefresh(swipeRefreshState)
        ) {
            LazyVerticalGrid(
                modifier = Modifier.padding(bottom = 80.dp),
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
                        MovieItemCard(item, onMovieClick = onMovieClick)
                    }
                    //  Text("Index=$index: $item", fontSize = 20.sp)
                }/*item {

                    *//* if (lazyPagingItems.loadState.append == LoadState.Loading) {

                             CircularProgressIndicator(
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .wrapContentWidth(Alignment.CenterHorizontally)
                             )
                         }*//*
                }*/
            }
            PullRefreshIndicator(
                lazyPagingItems.loadState.append == LoadState.Loading,
                swipeRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    })
}
