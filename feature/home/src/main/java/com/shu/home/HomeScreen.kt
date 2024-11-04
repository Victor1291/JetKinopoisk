package com.shu.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.shu.models.FilmVip
import com.shu.models.ManyScreens

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    manyScreens: ManyScreens,
    innerPadding: PaddingValues,
    onMovieClick: (Int?) -> Unit,
    onPostClick: (Int?) -> Unit,
    onListClick: (FilmVip?) -> Unit,
    viewModel: HomeViewModel
) {

    val refreshing by remember { mutableStateOf(false) }

    val swipeRefreshState =
        rememberPullRefreshState(refreshing, { viewModel.retry() })

    Box(
        Modifier
            .fillMaxSize()
            .pullRefresh(swipeRefreshState)
    ) {
        LazyColumn(contentPadding = innerPadding) {

            item {
                LazyRowPosts(posts = viewModel.listPostCashed.collectAsLazyPagingItems(), onPostClick = onPostClick)
            }

            items(manyScreens.homeListScreen.size) { num ->

                LazyRowMovie(
                    list = manyScreens.homeListScreen[num],
                    title = manyScreens.listTitle[num],
                    vip = if (num == 3) manyScreens.filmVipOne else manyScreens.filmVipTwo,
                    num = num,
                    onMovieClick = onMovieClick,
                    onListClick = onListClick
                )

            }
        }
        PullRefreshIndicator(
            refreshing,
            swipeRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}








