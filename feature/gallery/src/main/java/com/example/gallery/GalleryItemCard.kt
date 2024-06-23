package com.example.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shu.models.gallery_models.GalleryItem

@Composable
fun GalleryItemCard(
    gallery: GalleryItem,
    onMovieClick: (Int?) -> Unit,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(4.dp)
            .clickable { },

        ) {

        AsyncImage(
            modifier = Modifier,
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(gallery.imageUrl).build(),
            contentDescription = "picture",
          //  contentScale = ContentScale.FillBounds,
        )
    }
}
