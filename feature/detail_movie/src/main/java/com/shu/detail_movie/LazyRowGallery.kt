package com.shu.detail_movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shu.models.FilmVip
import com.shu.models.gallery_models.ListGalleryItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LazyRowGallery(
    gallery: ListGalleryItems,
    onGalleryClick: (String?) -> Unit,
    onAllClick: (Int?) -> Unit,
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
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 4.dp, top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Галерея",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
            )
            //TODO добавить стрелку иконку
            Text(
                text = "All",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                modifier = Modifier
                    .clickable { onAllClick(0) },
                //TODO реализовать передачу коллекций Vip по клику
            )
        }

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
