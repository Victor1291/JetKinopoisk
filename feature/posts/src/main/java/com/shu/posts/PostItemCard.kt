package com.shu.posts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
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
    onPostClick: (Int?) -> Unit,
    onExpandedClick: (Boolean) -> Unit,
    modifier: Modifier,
    expanded: Boolean,
    onNextPageClick: () -> Unit,
) {

    val uriHandler = LocalUriHandler.current
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth()
                .height(270.dp)
                .clickable {
                    onPostClick(post.kinopoiskId)
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
                text = " ${post.title} ${post.publishedAt}...${post.page} ",
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
                    .background(Color.Transparent.copy(alpha = 0.3f)),
            )

            IconButton(
                modifier = Modifier
                    .padding(end = 16.dp, top = 16.dp)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
                    .background(Color.Transparent.copy(alpha = 0.3f)),
                enabled = true,
                onClick = { post.url?.let { uriHandler.openUri(it) } },
            ) {
                Icon(
                    Icons.Default.Home,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(56.dp),
                    contentDescription = "collection"
                )
            }

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
