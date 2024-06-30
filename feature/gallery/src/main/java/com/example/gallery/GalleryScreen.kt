package com.example.gallery

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.design_system.component.TopBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GalleryScreen(
    modifier: Modifier,
    filmId: Int?,
    viewModel: GalleryViewModel = hiltViewModel(),
    list: List<String> = listOf(
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
    val cellConfiguration = if (LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE) {
        StaggeredGridCells.Adaptive(minSize = 175.dp)
    } else StaggeredGridCells.Fixed(2)

    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        if (filmId != null) {
            viewModel.setId(filmId)
        }
    }
    Column(
        modifier = modifier
    ) {
        TopBar(
            header = "Галерея",
            leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            onLeftIconClick = { navController.navigateUp() },
        )


        LazyRow(
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier,
        ) {

            items(list.size) { category ->

                InputChip(
                    onClick = {
                        select.intValue = category
                        if (viewModel.title != list[category]) {
                            viewModel.title = list[category]
                            lazyPagingItems.refresh()
                        }
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
            LazyVerticalStaggeredGrid(
                columns = cellConfiguration,
            ) {

                if (lazyPagingItems.itemCount != 0) {
                    expanded = false
                    items(count = lazyPagingItems.itemCount) { index ->
                        val item = lazyPagingItems[index]
                        if (item != null) {
                            GalleryItemCard(item, onMovieClick = {})
                        }
                    }
                } else {
                    expanded = true
                }
            }
            if (expanded) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        text = "No picture. Sorry...",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                    )
                }
            }
            if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        text = "Loading...",
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
            PullRefreshIndicator(
                lazyPagingItems.loadState.append == LoadState.Loading,
                swipeRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

