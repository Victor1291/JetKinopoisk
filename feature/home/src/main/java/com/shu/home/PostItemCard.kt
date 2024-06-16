package com.shu.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shu.models.media_posts.Post

@Composable
fun PostItemCard(
    post: Post,
    onMovieClick: (Int?) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .width(400.dp)
            .height(270.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent, Color.Transparent, Color.Gray
                    )
                ), shape = RectangleShape
            )
            .clickable { onMovieClick(post.kinopoiskId) },
    ) {

        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(post.imageUrl).build(),
            contentDescription = "picture",
            //contentScale = ContentScale.FillBounds,
        )

        Text(
            text = post.title ?: "",
            lineHeight = 25.sp,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(Dp(10f))
                .fillMaxWidth(1f),
            )
    }
}
