package com.shu.posts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostsScreen(
    innerPadding: PaddingValues,
    onPostClick: (Int?) -> Unit,
    viewModel: PostsViewModel = hiltViewModel()
) {

    val refreshing by remember { mutableStateOf(false) }

    val swipeRefreshState =
        rememberPullRefreshState(refreshing, { viewModel.refresh() })

    Box(
        Modifier
            .fillMaxSize()
            .pullRefresh(swipeRefreshState)
    ) {


        viewModel.listPostCashed?.let {
            LazyPosts(
                viewModel = viewModel,
                posts = it.collectAsLazyPagingItems(),
                onPostClick = onPostClick,
                onNextPageClick = { }
            )
        }

        PullRefreshIndicator(
            refreshing,
            swipeRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}









