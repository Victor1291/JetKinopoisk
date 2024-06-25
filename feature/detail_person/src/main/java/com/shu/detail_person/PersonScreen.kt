package com.shu.detail_person

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shu.models.detail_person.MovieOfActor
import com.shu.models.detail_person.Person

@Composable
fun PersonScreen(
    person: Person,
    modifier: Modifier = Modifier,
    onMovieClick: (Int?) -> Unit,
) {
    val select = remember {
        mutableIntStateOf(0)
    }
    val mainList = remember {
        mutableStateOf<List<MovieOfActor>>(emptyList())
    }
    LaunchedEffect(key1 = 2) {


        mainList.value = person.listMovies["Актёр"] ?: emptyList()
    }
    val uriHandler = LocalUriHandler.current

    LazyColumn(
        contentPadding = PaddingValues(4.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {

            AsyncImage(
                modifier = Modifier
                    .height(320.dp)
                    .width(210.dp),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(person.posterUrl).crossfade(true).build(),
                contentDescription = "poster",
                //   contentScale = ContentScale.FillBounds,
            )
        }

        item {
            Text(
                text = person.nameRu ?: "",
            )

        }
        item {
            Text(
                text = person.profession ?: "",
            )
        }
        item {
            Text(
                text = "${person.age} old ${person.birthday}",
            )
        }
        item {
            Text(
                text = person.birthplace ?: "",
            )
        }
        item {
            IconButton(
                modifier = Modifier.height(48.dp),
                enabled = true,
                onClick = { person.webUrl?.let { uriHandler.openUri(it) } },
            ) {
                Icon(
                    Icons.Default.Home,
                    tint = Color.LightGray,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                        .size(56.dp)
                        .border(1.dp, Color.Black),
                    contentDescription = "collection"
                )
            }
        }

        item {
            Text(
                text = "Фильмография ${person.films.size} фильмов ",
            )
        }

        item {

            LazyRow(
                contentPadding = PaddingValues(4.dp),
                modifier = Modifier,
            ) {

                items(person.listButtons.size) { category ->

                    InputChip(
                        onClick = {
                            select.intValue = category
                            mainList.value =
                                person.listMovies[person.listButtons[category]] ?: emptyList()
                        },
                        label = { Text(person.listButtons[category]) },
                        selected = category == select.intValue,
                    )

                }
            }
        }

        items(mainList.value.size) {
            if (mainList.value[it].nameRu != null) {
                ItemMovie(mainList.value[it], onMovieClick = onMovieClick)
            }
        }
    }
}

@Composable
private fun ItemMovie(
    movie: MovieOfActor,
    onMovieClick: (Int?) -> Unit,
) {
    Column(
        modifier = Modifier.clickable { onMovieClick(movie.filmId) },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalDivider()
        if (movie.rating.isNullOrEmpty()) {
            Text(text = movie.nameRu.toString())
        } else {
            Text(text = " ${movie.rating.toString()}  ${movie.nameRu.toString()}")
        }
        if (movie.description?.isNotEmpty() == true) {
            Text(
                text =
                if (movie.general == true) "главная роль ${movie.description.toString()}" else "роль ${movie.description.toString()}"
            )
        }

    }
}
