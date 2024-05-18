package com.shu.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shu.models.CinemaItem

@Composable
fun HoursCard(
    cinemaItem: CinemaItem
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.padding(4.dp)

    ) {

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,

            modifier = Modifier
                .padding(4.dp)
        ) {

            Text(
                text = cinemaItem.nameRu.toString(),
                color = MaterialTheme.colorScheme.primary
            )

            cinemaItem.genres?.let {
                Text(
                    text = if (it.isNotEmpty()) "${it[0]}" else "",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            AsyncImage(
                modifier = Modifier
                    .width(80.dp)
                    .height(200.dp),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(cinemaItem.posterUrlPreview)
                    .build(),
                contentDescription = "icon"
            )
            /*Text(
                text =  hours.pressureIn.toString() ,
                color = MaterialTheme.colorScheme.primary
            )*/
        }
    }
}