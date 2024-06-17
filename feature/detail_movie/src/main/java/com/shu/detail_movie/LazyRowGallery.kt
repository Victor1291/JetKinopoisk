package com.shu.detail_movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shu.models.gallery_models.ListGalleryItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LazyRowGallery(
    gallery: ListGalleryItems,
    onGalleryClick: (String?) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = 1) {
        val max = gallery.items.size
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

    Column(
        modifier = Modifier
    ) {
        Text(
            text = "Галерея",
            color = MaterialTheme.colorScheme.primary
        )
        LazyRow(
            contentPadding = PaddingValues(4.dp),
            modifier = modifier,
            state = state
        ) {

            gallery.items.let { listHours ->
                listHours.size?.let {
                    items(it) { id ->
                        GalleryItemCard(gallery.items[id], onGalleryClick = onGalleryClick)
                    }
                }
            }
        }
    }
}
