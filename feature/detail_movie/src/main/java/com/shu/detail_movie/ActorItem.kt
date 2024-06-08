package com.shu.detail_movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.shu.models.details.Actor

@Composable
fun ActorItem(
    actor: Actor,
    onActorClick: (Int?) -> Unit,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(4.dp)
            .width(230.dp)
            .height(100.dp)
            .clickable { onActorClick(actor.staffId) },

        ) {

        Row(
            modifier = Modifier.padding(4.dp)
        ) {

            AsyncImage(
                modifier = Modifier
                    .width(55.dp)
                    .height(74.dp),
                model = ImageRequest.Builder(context = LocalContext.current).data(actor.posterUrl)
                    .build(),
                contentDescription = "icon"
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,

                modifier = Modifier
                    .padding(4.dp)
                    .width(150.dp),
            ) {

                actor.nameRu?.let {
                    Text(
                        text = it,
                        maxLines = 1,
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                actor.description?.let {
                    Text(
                        text = it,
                        maxLines = 1,
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}