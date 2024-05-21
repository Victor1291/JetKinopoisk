package com.shu.detail_movie

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shu.models.details.DetailMovie


val gray100 = Color(0xffe5e5e5)
val gray200 = Color(0xffd0d0d0)

@Composable
fun DetailScreen(
    movie: DetailMovie,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    onMovieClick: (Int?) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(4.dp), modifier = modifier, state = state
    ) {
        item {
            Box( modifier = Modifier.clickable {

            }) {

                if (movie.coverUrl != null) {

                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp),
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(movie.coverUrl).crossfade(true).build(),
                        contentDescription = "poster",
                        contentScale = ContentScale.FillBounds,
                        alpha = 0.8f
                    )

                    Shadow()

                    if (movie.logoUrl != null) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp),
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(movie.logoUrl).crossfade(true).build(),
                            contentDescription = "logo",

                            )
                    } else {
                        Text(
                            text = movie.nameRu ?: "",
                            lineHeight = 35.sp,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                } else {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp),
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(movie.posterUrl).crossfade(true).build(),
                        contentDescription = "poster",
                        contentScale = ContentScale.FillBounds,
                    )

                    Shadow()
                }

               // Buttons()

                UserInputSelector(
                    onSelectorChange = {},
                    sendMessageEnabled = true,
                    onMessageSent = {},
                    currentInputSelector = InputSelector.DM,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
        item {
            Text(
                text = movie.nameRu ?: "",
                lineHeight = 35.sp,
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Text(
                text = movie.description ?: "",
                lineHeight = 35.sp,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = movie.shortDescription ?: "",
                lineHeight = 35.sp,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }


        /* items(manyScreens.homeListScreen.size) { num ->

             LazyRowMovie(
                 list = manyScreens.homeListScreen[num],
                 num,
                 onMovieClick = onMovieClick
             )

         }*/
    }
}

@Composable
fun Buttons(
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = Modifier
            .width(200.dp)
            .height(120.dp)

    ) {
        Row(
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Icon(
                imageVector = Icons.Outlined.Favorite,
                contentDescription = "icon for navigation item"
            )
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "icon for navigation item"
            )
        }
        /*Text("This text is drawn first", modifier = Modifier.align(Alignment.TopCenter))
        Box(
            Modifier.align(Alignment.TopCenter).fillMaxHeight().width(
                50.dp
            ).background(Color.Blue)
        )
        Text("This text is drawn last", modifier = Modifier.align(Alignment.Center))*/
    }
}

@Composable
fun Shadow(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .alpha(0.1f)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        gray100, gray200, Color.Gray
                    )
                ), shape = RectangleShape
            )
    ) {
    }
}

@Composable
fun UserInput(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
    ) {
            UserInputSelector(
                onSelectorChange = {},
                sendMessageEnabled = true,
                onMessageSent = {},
                currentInputSelector = InputSelector.DM,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
    }
}

@Composable
fun DiagonalColumn(
    modifier: Modifier = Modifier, content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier, content = content
    ) { measurables, constraints ->

        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        layout(constraints.maxWidth, constraints.maxHeight) {
            var x = 0
            var y = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(x = x, y = y)
                x += placeable.width
                y += placeable.height
            }
        }
    }
}


@Composable
private fun UserInputSelector(
    onSelectorChange: (InputSelector) -> Unit,
    sendMessageEnabled: Boolean,
    onMessageSent: () -> Unit,
    currentInputSelector: InputSelector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(72.dp)
            .wrapContentHeight()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InputSelectorButton(
            onClick = { onSelectorChange(InputSelector.EMOJI) },
            icon = Icons.Outlined.Favorite,
            selected = currentInputSelector == InputSelector.EMOJI,
            description = stringResource(id = R.string.emoji_selector_bt_desc)
        )
        InputSelectorButton(
            onClick = { onSelectorChange(InputSelector.DM) },
            icon = Icons.Outlined.Star,
            selected = currentInputSelector == InputSelector.DM,
            description = stringResource(id = R.string.emoji_selector_bt_desc)
        )
        InputSelectorButton(
            onClick = { onSelectorChange(InputSelector.PICTURE) },
            icon = Icons.Outlined.Search,
            selected = currentInputSelector == InputSelector.PICTURE,
            description = stringResource(id = R.string.emoji_selector_bt_desc)
        )
        InputSelectorButton(
            onClick = { onSelectorChange(InputSelector.MAP) },
            icon = Icons.Outlined.Place,
            selected = currentInputSelector == InputSelector.MAP,
            description = stringResource(id = R.string.emoji_selector_bt_desc)
        )
        InputSelectorButton(
            onClick = { onSelectorChange(InputSelector.PHONE) },
            icon = Icons.Outlined.Person,
            selected = currentInputSelector == InputSelector.PHONE,
            description = stringResource(id = R.string.emoji_selector_bt_desc)
        )

        val border = if (!sendMessageEnabled) {
            BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
        } else {
            null
        }
        Spacer(modifier = Modifier.weight(1f))

        val disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

        val buttonColors = ButtonDefaults.buttonColors(
            disabledContainerColor = Color.Transparent,
            disabledContentColor = disabledContentColor
        )

        // Send button
        Button(
            modifier = Modifier.height(36.dp),
            enabled = sendMessageEnabled,
            onClick = onMessageSent,
            colors = buttonColors,
            border = border,
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                stringResource(id = R.string.dots),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun InputSelectorButton(
    onClick: () -> Unit,
    icon: ImageVector,
    description: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundModifier = if (selected) {
        Modifier.background(
            color = LocalContentColor.current,
            shape = RoundedCornerShape(14.dp)
        )
    } else {
        Modifier
    }
    IconButton(
        onClick = onClick,
        modifier = modifier //.then(backgroundModifier)
    ) {
        val tint = if (selected) {
           Color.Red// contentColorFor(backgroundColor = LocalContentColor.current)
        } else {
            Color.White//LocalContentColor.current
        }
        Icon(
            icon,
            tint = tint,
            modifier = Modifier
                .padding(8.dp)
                .size(56.dp),
            contentDescription = description
        )
    }
}

enum class InputSelector {
    NONE,
    MAP,
    DM,
    EMOJI,
    PHONE,
    PICTURE
}

enum class EmojiStickerSelector {
    EMOJI,
    STICKER
}
