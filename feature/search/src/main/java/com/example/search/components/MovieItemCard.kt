package com.example.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
            .wrapContentSize()
            .clickable { onMovieClick(cinemaItem.kinopoiskId) },

        ) {

        Box {

            AsyncImage(
                modifier = Modifier
                    .padding(4.dp)
                    .width(180.dp)
                    .height(270.dp),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(cinemaItem.posterUrlPreview).build(),
                contentDescription = "picture",
                contentScale = ContentScale.FillBounds,
            )
            if (cinemaItem.nameRu != null) {

                Text(
                    text = cinemaItem.nameRu ?: "",
                    lineHeight = 20.sp,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.BottomStart)
                )

            } else if (cinemaItem.nameEn != null) {
                Text(
                    text = cinemaItem.nameEn ?: "",
                    lineHeight = 20.sp,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.BottomStart)
                )
            } else if (cinemaItem.nameOriginal != null) {
                Text(
                    text = cinemaItem.nameOriginal ?: "",
                    lineHeight = 20.sp,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.BottomStart)
                )
            }
        }
    }
}
