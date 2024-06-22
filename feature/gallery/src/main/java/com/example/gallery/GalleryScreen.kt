package com.example.gallery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.InputChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GalleryScreen(
    filmId: Int?,
    viewModel: GalleryViewModel = hiltViewModel(),
    list: List<String> = listOf<String>(
        "STILL",
        "SHOOTING",
        "POSTER",
        "FAN_ART",
        "PROMO",
        "CONCEPT",
        "WALLPAPER",
        "COVER",
        "SCREENSHOT"
    ),
    navController: NavHostController,
) {

    val lazyPagingItems = viewModel.pagedMovies.collectAsLazyPagingItems()

    val swipeRefreshState =
        rememberPullRefreshState(false, onRefresh = { lazyPagingItems.refresh() })

    val select = remember {
        mutableIntStateOf(0)
    }
    val mainList = remember {
        mutableStateOf<List<String>>(list)
    }
    LaunchedEffect(key1 = true) {
        if (filmId != null) {
            viewModel.setId(filmId)
        }
    }
    Scaffold(
        topBar = {
            TopBar(
                header = "",
                leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                onLeftIconClick = { navController.navigateUp() },
            )
        },
        content = { innerPadding ->
            val modifier = Modifier.padding(innerPadding)

            Column(
                modifier = modifier,
            ) {

                LazyRow(
                    contentPadding = PaddingValues(4.dp),
                    modifier = Modifier,
                ) {

                    items(list.size) { category ->

                        InputChip(
                            onClick = {
                                select.intValue = category
                                viewModel.setTitle(list[category])
                                lazyPagingItems.refresh()
                            },
                            label = { Text(list[category]) },
                            selected = category == select.intValue,
                        )
                    }
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
                                GalleryItemCard(item, onMovieClick = {})
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
        })
}