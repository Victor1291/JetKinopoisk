package com.example.profile

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
import com.shu.models.CinemaItem
import com.shu.models.similar_models.SimilarItem

@Composable
fun MovieItemCard(
    cinemaItem: CinemaItem,
    onMovieClick: (Int?) -> Unit,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(4.dp)
            .width(180.dp)
            .height(270.dp)
            .clickable { onMovieClick(cinemaItem.kinopoiskId) },

        ) {

        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(cinemaItem.posterUrlPreview).build(),
            contentDescription = "picture",
            contentScale = ContentScale.FillBounds,
        )
    }
}