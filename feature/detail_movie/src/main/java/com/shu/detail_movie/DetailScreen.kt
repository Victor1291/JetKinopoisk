package com.shu.detail_movie

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shu.models.details.Actor
import com.shu.models.details.DetailMovie
import com.shu.models.gallery_models.ListGalleryItems
import com.shu.models.similar_models.ListSimilar

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    movie: DetailMovie,
    actors: List<Actor>,
    gallery: ListGalleryItems,
    similar: ListSimilar,
    modifier: Modifier = Modifier,
    onMovieClick: (Int?) -> Unit,
    onGalleryClick: (String?) -> Unit,
    onActorClick: (Int?) -> Unit,
    onCollectionClick: () -> Unit,
    onShareClick: (String) -> Unit,
    onBrowsingClick: (String) -> Unit,
    onAllClick: (Int?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    var favorite by remember {
        mutableStateOf(movie.favorite)
    }
    var seeLater by remember {
        mutableStateOf(movie.seeLater)
    }
    var watched by remember {
        mutableStateOf(movie.watched)
    }
    LazyColumn(
        contentPadding = PaddingValues(4.dp), modifier = modifier,
    ) {
        item {
            Box(modifier = Modifier.clickable {

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
                    )

                    if (movie.logoUrl != null) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                                .align(Alignment.BottomCenter),
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(movie.logoUrl).crossfade(true).build(),
                            contentDescription = "logo",

                            )
                    } else {
                        Text(
                            text = movie.nameRu ?: "",
                            lineHeight = 35.sp,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .align(Alignment.BottomCenter)
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
                }
                // Buttons()

            }
        }
        item {
            ElevatedCard(elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ), modifier = Modifier.clickable { expanded = !expanded }) {
                Row(
                    modifier = modifier
                        .wrapContentHeight()
                        .background(Color.Black),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    InputSelectorButton(
                        onClick = {
                            favorite = !favorite
                            viewModel.toggleFavoriteStatus(favorite)
                        },
                        icon = painterResource(id = R.drawable.icon_heart),
                        selected = favorite,
                        description = stringResource(id = R.string.heart)
                    )
                    InputSelectorButton(
                        onClick = {
                            seeLater = !seeLater
                            viewModel.toggleSeeLaterStatus(seeLater)
                        },
                        icon = painterResource(id = R.drawable.icon_select),
                        selected = seeLater,
                        description = stringResource(id = R.string.select)
                    )
                    InputSelectorButton(
                        onClick = {
                            watched = !watched
                            viewModel.toggleWatchedStatus(watched)
                        },
                        icon = painterResource(id = R.drawable.icon_see_movie),
                        selected = watched,
                        description = stringResource(id = R.string.see)
                    )
                    InputSelectorButton(
                        onClick = { movie.imdbId?.let { onShareClick(it) } },
                        icon = painterResource(id = R.drawable.icon_share),
                        selected = false,
                        description = stringResource(id = R.string.share)
                    )
                    InputSelectorButton(
                        onClick = { movie.webUrl?.let { onBrowsingClick(it) } },
                        icon = painterResource(id = android.R.drawable.ic_dialog_info),
                        selected = false,
                        description = stringResource(id = R.string.brouser)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Send button
                    IconButton(
                        modifier = Modifier.height(36.dp),
                        enabled = true,
                        onClick = onCollectionClick,
                    ) {
                        Icon(
                            painterResource(id = R.drawable.icon_menu),
                            tint = Color.White,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(56.dp),
                            contentDescription = "collection"
                        )
                    }
                }
            }
        }

        item {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ), modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize()
            ) {
                /*  Text(
                      text = movie.ratingKinopoisk?.let { "$it ${movie.nameRu}" }
                          ?: "${movie.nameRu}",
                      lineHeight = 35.sp,
                      fontSize = 25.sp,
                      fontWeight = FontWeight.Medium,
                      maxLines = 2,
                      overflow = TextOverflow.Ellipsis,
                      textAlign = TextAlign.Center,
                  )*/
                Text(
                    text = movie.year?.let { year ->
                        "$year ${movie.genresList}"
                    } ?: movie.genresList,
                    lineHeight = 16.sp,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = movie.cityRateFilmLength,
                    lineHeight = 16.sp,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                )
            }
        }
        item {

            if (movie.shortDescription != null) {
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { expanded = !expanded },
                ) {
                    AnimatedVisibility(!expanded) {
                        Text(
                            text = "${movie.shortDescription}...",
                            lineHeight = 20.sp,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            //  maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    AnimatedVisibility(expanded) {
                        Text(
                            text = movie.description ?: "",
                            lineHeight = 20.sp,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            // maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            } else if (movie.description != null) {
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { expanded = !expanded },
                ) {
                    AnimatedVisibility(!expanded) {
                        Text(
                            text = movie.description.toString(),
                            lineHeight = 20.sp,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    AnimatedVisibility(expanded) {
                        Text(
                            text = movie.description.toString(),
                            lineHeight = 20.sp,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }


        item {
            GridActors(actors = actors, onActorClick = onActorClick)
        }

        item {

            if (gallery.items.isNotEmpty()) {
                LazyRowGallery(
                    gallery = gallery,
                    onAllClick = { onAllClick(movie.kinopoiskId) },
                    onGalleryClick = onGalleryClick
                )
            }
        }

        item {
            if (similar.items.isNotEmpty()) {
                LazyRowSimilar(similar = similar, onMovieClick = onMovieClick)
            }
        }
    }
}


/*@Composable
private fun UserInputSelector(
    viewModel: DetailViewModel,
    movie: DetailMovie,
    onCollectionClick: () -> Unit,
    onShareClick: () -> Unit,
    onBrowsingClick: () -> Unit,
    modifier: Modifier = Modifier,
    favorite: Boolean,
    seeLater: Boolean,
    watched: Boolean,
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .background(Color.Blue),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InputSelectorButton(
            onClick = {
                favorite = !favorite
                viewModel.toggleFavoriteStatus(favorite)
            },
            icon = painterResource(id = R.drawable.icon_heart),
            selected = favorite,
            description = stringResource(id = R.string.heart)
        )
        InputSelectorButton(
            onClick = {
                seeLater = !seeLater
                viewModel.toggleSeeLaterStatus(seeLater)
            },
            icon = painterResource(id = R.drawable.icon_select),
            selected = seeLater,
            description = stringResource(id = R.string.select)
        )
        InputSelectorButton(
            onClick = {
                watched = !watched
                viewModel.toggleWatchedStatus(watched)
            },
            icon = painterResource(id = R.drawable.icon_see_movie),
            selected = watched,
            description = stringResource(id = R.string.see)
        )
        InputSelectorButton(
            onClick = { onShareClick() },
            icon = painterResource(id = R.drawable.icon_share),
            selected = false,
            description = stringResource(id = R.string.share)
        )
        InputSelectorButton(
            onClick = { onBrowsingClick() },
            icon = painterResource(id = R.drawable.icon_share),
            selected = false,
            description = stringResource(id = R.string.brouser)
        )

        Spacer(modifier = Modifier.weight(1f))


        // Send button
        IconButton(
            modifier = Modifier.height(36.dp),
            enabled = true,
            onClick = onCollectionClick,

            ) {
            Icon(
                painterResource(id = R.drawable.icon_share),
                tint = Color.Yellow,
                modifier = Modifier
                    .padding(8.dp)
                    .size(56.dp),
                contentDescription = "collection"
            )
        }
    }
}*/

@Composable
private fun InputSelectorButton(
    onClick: () -> Unit,
    icon: Painter,
    description: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick, modifier = modifier //.then(backgroundModifier)
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