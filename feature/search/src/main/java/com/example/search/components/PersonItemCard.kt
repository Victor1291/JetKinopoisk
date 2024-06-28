package com.example.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shu.models.detail_person.SearchPerson

@Composable
fun PersonItemCard(
    actor: SearchPerson,
    onActorClick: (Int?) -> Unit,
) {
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(230.dp)
            .clickable { onActorClick(actor.personId) },
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(actor.posterUrl)
                .build(),
            contentDescription = "icon",
            contentScale = ContentScale.FillBounds,
        )
        actor.nameRu?.let {
            Text(
                text = it,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(Color.Transparent.copy(alpha = 0.3f)),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}



