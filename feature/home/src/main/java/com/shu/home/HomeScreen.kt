package com.shu.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.shu.models.FilmVip
import com.shu.models.ManyScreens
import com.shu.models.media_posts.ListPosts

@Composable
fun HomeScreen(
    manyScreens: ManyScreens,
    innerPadding: PaddingValues,
    posts: ListPosts,
    onMovieClick: (Int?) -> Unit,
    onPostClick: (Int?) -> Unit,
    onListClick: (FilmVip?) -> Unit,
) {

    LazyColumn(contentPadding = innerPadding) {

        item {
            LazyRowPosts(posts = posts, onPostClick = onPostClick)
        }

        items(manyScreens.homeListScreen.size) { num ->

            LazyRowMovie(
                list = manyScreens.homeListScreen[num],
                num = num,
                onMovieClick = onMovieClick,
                onListClick = onListClick
            )

        }
    }
}







