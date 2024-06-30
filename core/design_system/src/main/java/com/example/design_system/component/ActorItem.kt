package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
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
    Box(
        modifier = Modifier
            .width(70.dp)
            .height(120.dp)
            .clickable { onActorClick(actor.staffId) },
    ) {

        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(context = LocalContext.current).data(actor.posterUrl)
                .build(),
            contentDescription = "icon",
            contentScale = ContentScale.FillBounds,
        )

        Text(
            text = "${if (actor.nameRu.isNullOrEmpty()) actor.nameEn else actor.nameRu}",
            lineHeight = 13.sp,
            fontSize = 12.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .background(Color.Transparent.copy(alpha = 0.3f))
                .align(Alignment.BottomStart),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}



