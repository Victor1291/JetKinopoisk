package com.shu.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    onMovieClick: (Int?) -> Unit,
    onPostClick: (Int?) -> Unit,
    onListClick: (FilmVip?) -> Unit,
) {

    Column(
        modifier = Modifier
    ) {
        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn(
            contentPadding = PaddingValues(4.dp),
            modifier = modifier.padding(top = 10.dp, bottom = 110.dp),
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
        Spacer(modifier = Modifier.height(50.dp))
    }
}







