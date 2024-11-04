package com.shu.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.shu.models.media_posts.Post


@Composable
fun LazyRowPosts(
    posts: LazyPagingItems<Post>,
    onPostClick: (Int?) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    // val coroutineScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    /* LaunchedEffect(key1 = 1) {
         val max = posts.items.size
         coroutineScope.launch {
             while (true) {
                 repeat(max) {
                     delay(5000)
                     state.animateScrollToItem(state.firstVisibleItemIndex + 1)
                 }
                 state.scrollToItem(0)
             }
         }
     }*/
    LazyRow(
        contentPadding = PaddingValues(4.dp), modifier = modifier.padding(end = 8.dp),
        state = state,
    ) {

        items(posts.itemCount) { index ->
            posts[index]?.let { post ->
                PostItemCard(
                    post,
                    onPostClick = onPostClick,
                    onExpandedClick = { expanded = it },
                    modifier = modifier,
                    expanded = expanded
                )
            }
        }
    }

}


