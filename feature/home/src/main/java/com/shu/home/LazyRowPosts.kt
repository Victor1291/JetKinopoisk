package com.shu.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shu.models.media_posts.ListPosts
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun LazyRowPosts(
    posts: ListPosts,
    onMovieClick: (Int?) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = 1) {
        val max = posts.items.size
        coroutineScope.launch {
            while(true) {
                repeat(max) {
                    delay(2000)
                    state.animateScrollToItem(state.firstVisibleItemIndex + 1)
                }
                state.scrollToItem(0)
            }
        }
    }
    LazyRow(
        contentPadding = PaddingValues(4.dp), modifier = modifier,
        state = state,
    ) {

        posts.items.size.let { size ->
            items(size) { index ->
                //TODO по клику окрывать url или искать сериал или фильм по id
                PostItemCard(posts.items[index], onMovieClick = {  })
            }
        }
    }
}


