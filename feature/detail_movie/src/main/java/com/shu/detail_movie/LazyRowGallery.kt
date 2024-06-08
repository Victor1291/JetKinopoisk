package com.shu.detail_movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shu.models.gallery_models.ListGalleryItems

@Composable
fun LazyRowGallery(
    gallery: ListGalleryItems,
    onGalleryClick: (String?) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
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
