package com.shu.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.shu.models.media_posts.Post

@Composable
fun PostItemCard(
    post: Post,
    onMovieClick: (Int?) -> Unit,
    onExpandedClick: (Boolean) -> Unit,
    modifier: Modifier,
    expanded: Boolean
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = modifier
                .padding(4.dp)
                .width(350.dp)
                .height(270.dp)
                .clickable {
                    onMovieClick(post.kinopoiskId)
                    onExpandedClick(!expanded)
                },
        ) {

            AsyncImage(
                modifier = modifier,
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(post.imageUrl).build(),
                contentDescription = "picture",
                contentScale = ContentScale.FillBounds,
            )

            Text(
                text = post.title ?: "",
                lineHeight = 20.sp,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.BottomCenter)
            )
        }
        AnimatedVisibility(expanded) {
            Text(
                text = post.description ?: "",
                lineHeight = 20.sp,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                // maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(350.dp)
                    .padding(4.dp)
            )
        }
    }
}
