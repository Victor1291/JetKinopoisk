package com.shu.list_movies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.shu.models.FilmVip

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListScreen(
    filmVip: FilmVip?,
    listViewModel: ListViewModel = hiltViewModel(),
    navController: NavHostController,
    onMovieClick: (Int?) -> Unit
) {
    Scaffold(topBar = {
        TopBar(
            header = filmVip?.title?.name ?: "",
            leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            onLeftIconClick = { navController.navigateUp() },
        )
    }, content = { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        // var refreshing by remember { mutableStateOf(false) }
        //   val lazyMovieItems: LazyPagingItems<CinemaItem> = listViewModel.pagedMovies.collectAsLazyPagingItems()
        LaunchedEffect(key1 = true) {
            if (filmVip != null) {
                listViewModel.setTitle(filmVip)
            }
        }


        val lazyPagingItems = listViewModel.pagedMovies.collectAsLazyPagingItems()

        val swipeRefreshState =
            rememberPullRefreshState(false, onRefresh = { lazyPagingItems.refresh() })

        Box(
            Modifier
                .fillMaxSize()
                .pullRefresh(swipeRefreshState)
        ) {
            LazyVerticalGrid(
                modifier = Modifier.padding(bottom = 80.dp),
                columns = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(4.dp),
            ) {
                if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
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
                }
                /*item {

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
