package com.shu.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shu.models.FilmVip
import com.shu.models.ManyScreens
import com.shu.models.media_posts.ListPosts


@Composable
fun HomeScreen(
    manyScreens: ManyScreens,
    posts: ListPosts,
    modifier: Modifier ,
    state: LazyListState = rememberLazyListState(),
    onMovieClick: (Int?) -> Unit,
    onPostClick: (Int?) -> Unit,
    onListClick: (FilmVip?) -> Unit,
) {

    Column(
       modifier = modifier,
    ) {

        LazyColumn(
            contentPadding = PaddingValues(4.dp),
            state = state
        ) {

            item {
                LazyRowPosts(posts = posts, onPostClick = onPostClick)
            }

            items(manyScreens.homeListScreen.size) { num ->

                LazyRowMovie(
                    list = manyScreens.homeListScreen[num],
                    num,
                    onMovieClick = onMovieClick,
                    onListClick = onListClick
                )

            }
        }
    }
}







