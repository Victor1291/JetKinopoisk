package com.shu.detail_person

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.design_system.component.RowThreeText
import com.example.design_system.component.TopBar
import com.shu.detail_actor.R
import com.shu.models.detail_person.MovieOfActor
import com.shu.models.detail_person.Person

@Composable
fun PersonScreen(
    modifier: Modifier,
    person: Person,
    onMovieClick: (Int?) -> Unit,
    onLeftClick: () -> Unit,
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

    Column(
        modifier = modifier
    ) {

        TopBar(
            header = "",
            leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            onLeftIconClick = { onLeftClick() },
        )

        LazyColumn(
            contentPadding = PaddingValues(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {

                AsyncImage(
                    modifier = Modifier
                        .height(320.dp)
                        .width(230.dp),
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
                Button(
                    modifier = Modifier
                        .height(36.dp)
                        .fillMaxWidth(),
                    onClick = { person.webUrl?.let { uriHandler.openUri(it) } },
                ) {
                    Text(
                        stringResource(id = R.string.go_web),
                        modifier = Modifier.padding(horizontal = 16.dp)
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

                    val m = mainList.value[it]
                    RowThreeText(
                        rowId = m.filmId,
                        rating = if (m.rating.isNullOrEmpty()) "    " else " ${m.rating}",
                        first = if (m.nameRu.isNullOrEmpty()) "${m.nameEn}" else "${m.nameRu}",
                        second = if (m.description?.isNotEmpty() == true) {
                            if (m.general == true) "главная роль ${m.description.toString()}" else "роль ${m.description.toString()}"
                        } else "",
                        onClick = onMovieClick
                    )
                    HorizontalDivider(thickness = 2.dp)
                }
            }
        }
    }
}