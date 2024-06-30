package com.shu.detail_movie

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.design_system.component.RowTwoColumn
import com.shu.detail_movie.components.GridActors
import com.shu.detail_movie.components.LazyRowGallery
import com.shu.detail_movie.components.LazyRowSimilar
import com.shu.models.details.Actor
import com.shu.models.details.DetailMovie
import com.shu.models.gallery_models.ListGalleryItems
import com.shu.models.similar_models.ListSimilar

@Composable
fun DetailScreen(
    modifier: Modifier,
    viewModel: DetailViewModel,
    movie: DetailMovie,
    actors: List<Actor>,
    gallery: ListGalleryItems,
    similar: ListSimilar,
    onMovieClick: (Int?) -> Unit,
    onGalleryClick: (String?) -> Unit,
    onActorClick: (Int?) -> Unit,
    onCollectionClick: () -> Unit,
    onShareClick: (String) -> Unit,
    onBrowsingClick: (String) -> Unit,
    onAllClick: (Int?) -> Unit,
    onBackClick: () -> Unit,
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
        modifier = modifier, contentPadding = PaddingValues(4.dp)
    ) {


        // coverUrl, Logo, nameRu
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
                                .padding(bottom = 60.dp)
                                .align(Alignment.BottomCenter),
                            model = ImageRequest.Builder(context = LocalContext.current)
                                .data(movie.logoUrl).crossfade(true).build(),
                            contentDescription = "logo",

                            )
                    } else {
                        Text(
                            text = if (movie.nameRu.isNullOrEmpty()) "${movie.nameEn}" else "${movie.nameRu}",
                            lineHeight = 35.sp,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(bottom = 60.dp)
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
                // Back Button
                IconButton(
                    onClick = { onBackClick() },
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
                        .clip(CircleShape)
                        .background(Color.Transparent.copy(alpha = 0.3f))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = "back",
                        modifier = Modifier.padding(1.dp)
                    )
                }

                //Buttons Menu - favorite, seeLater,watched,  Share, Browsing
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                        .align(Alignment.BottomCenter),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
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
                        icon = painterResource(id = R.drawable.icon_browser),
                        selected = false,
                        description = stringResource(id = R.string.brouser)
                    )

                    // Spacer(modifier = Modifier.weight(1f))

                    // Collections Menu button
                    IconButton(
                        modifier = Modifier
                            .height(36.dp)
                            .clip(CircleShape)
                            .background(Color.Transparent.copy(alpha = 0.3f)),
                        enabled = true,
                        onClick = onCollectionClick,
                    ) {
                        Icon(
                            painterResource(id = R.drawable.icon_menu),
                            tint = Color.White,
                            modifier = Modifier
                                .padding(1.dp)
                                .size(56.dp),
                            contentDescription = "collection"
                        )
                    }
                }

            }
        }

        // Rating, genre , country, FilmLength, ratingAgeLimits
        item {

            RowTwoColumn(rowId = movie.kinopoiskId,
                rating = if (movie.ratingKinopoisk == null) "" else "${movie.ratingKinopoisk}",
                year = if (movie.year == null) "" else "${movie.year}",
                first = movie.genresList,
                second = movie.cityRateFilmLength,
                onClick = {})
        }

        //Description Movie with animation
        item {
            if (movie.shortDescription != null) {
                AnimatedCard(first = "${movie.shortDescription}...",
                    second = movie.description ?: "",
                    expanded = expanded,
                    onClick = { expanded = !expanded })
            } else if (movie.description != null) {

                AnimatedCard(first = movie.description ?: "",
                    second = movie.description ?: "",
                    expanded = expanded,
                    onClick = { expanded = !expanded })
            }
        }

        // Grids Person - Actor, Creator and itc.
        item {
            GridActors(
                actors = actors,
                onActorClick = onActorClick,
                onAllClick = { /*TODO*/ },
            )
        }

        // Gallery STILL
        item {

            if (gallery.items.isNotEmpty()) {
                LazyRowGallery(
                    gallery = gallery,
                    onAllClick = { onAllClick(movie.kinopoiskId) },
                    onGalleryClick = onGalleryClick
                )
            }
        }

        // Grid Similar Movie
        item {
            if (similar.items.isNotEmpty()) {
                LazyRowSimilar(similar = similar, onMovieClick = onMovieClick)
            }
        }
    }

}

@Composable
private fun AnimatedCard(
    first: String,
    second: String,
    expanded: Boolean,
    onClick: () -> Unit,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick() },
    ) {
        AnimatedVisibility(!expanded) {
            Text(
                text = first, lineHeight = 20.sp, fontSize = 15.sp, fontWeight = FontWeight.Medium,
                //  maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        AnimatedVisibility(expanded) {
            Text(
                text = second,
                lineHeight = 20.sp,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}

@Composable
private fun InputSelectorButton(
    onClick: () -> Unit,
    icon: Painter,
    description: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick, modifier = modifier
            .clip(CircleShape)
            .background(Color.Transparent.copy(alpha = 0.3f))
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
                .padding(1.dp)
                .size(56.dp),
            contentDescription = description
        )
    }
}