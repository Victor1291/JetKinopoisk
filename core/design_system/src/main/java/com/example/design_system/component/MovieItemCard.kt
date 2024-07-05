package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shu.models.CinemaItem

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
            .width(130.dp)
            .height(220.dp)
            .clickable { onMovieClick(cinemaItem.kinopoiskId) },

        ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(cinemaItem.posterUrlPreview).build(),
                contentDescription = "picture",
                contentScale = ContentScale.FillBounds,
            )

            Text(
                text = "${if (cinemaItem.nameRu.isNullOrEmpty()) cinemaItem.nameEn else cinemaItem.nameRu}",
                lineHeight = 15.sp,
                fontSize = 14.sp,
                modifier = Modifier
                    .testTag("movie")
                    .fillMaxWidth()
                    .background(Color.Transparent.copy(alpha = 0.3f))
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 4.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}