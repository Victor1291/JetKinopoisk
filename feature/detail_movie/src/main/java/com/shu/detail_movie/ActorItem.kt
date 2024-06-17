package com.shu.detail_movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shu.models.details.Actor

@Composable
fun ActorItem(
    actor: Actor,
    onActorClick: (Int?) -> Unit,
) {

    Column(
        modifier = Modifier
            .padding(4.dp)
            .width(70.dp)
            .height(150.dp)
    ) {

        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .width(70.dp)
                .height(100.dp)
                .clickable { onActorClick(actor.staffId) },

            ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(context = LocalContext.current).data(actor.posterUrl)
                    .build(),
                contentDescription = "icon",
                contentScale = ContentScale.FillBounds,
            )
        }

        actor.nameRu?.let {
            Text(
                text = it,
                maxLines = 2,

                lineHeight = 12.sp,
                fontSize = 10.sp,
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
        actor.description?.let {
            Text(
                text = it,
                maxLines = 1,
                lineHeight = 12.sp,
                fontSize = 8.sp,
                modifier = Modifier,
                color = MaterialTheme.colorScheme.primary
            )
        } ?: Text(
            text = actor.professionText.toString(),
            maxLines = 1,
            fontSize = 8.sp,
            modifier = Modifier,
            color = MaterialTheme.colorScheme.primary
        )
    }
}


