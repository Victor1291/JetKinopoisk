package com.shu.list_movies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.design_system.component.MovieItemCard
import com.example.design_system.component.TopBar
import com.shu.models.FilmVip

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListScreen(
    modifier: Modifier,
    innerPadding: PaddingValues,
    filmVip: FilmVip,
    navController: NavHostController,
    onMovieClick: (Int?) -> Unit
) {

    val listViewModel = hiltViewModel<ListViewModel, ListViewModel.Factory> { factory ->
        factory.create(filmVip)
    }
    val lazyPagingItems = listViewModel.listCashed.collectAsLazyPagingItems()

    val swipeRefreshState =
        rememberPullRefreshState(false, onRefresh = { lazyPagingItems.refresh() })

    Column(
        //  modifier = modifier
    ) {

        TopBar(
            header = filmVip?.title?.name ?: "",
            leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            onLeftIconClick = { navController.navigateUp() },
        )

        Box(
            Modifier
                .fillMaxSize()
                .pullRefresh(swipeRefreshState)
        ) {
            LazyVerticalGrid(
                // modifier = Modifier.padding(bottom = 80.dp),
                columns = GridCells.Adaptive(150.dp),
                contentPadding = innerPadding,
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
            }
            PullRefreshIndicator(
                lazyPagingItems.loadState.append == LoadState.Loading,
                swipeRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
