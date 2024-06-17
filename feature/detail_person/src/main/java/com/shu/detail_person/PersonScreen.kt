package com.shu.detail_person

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

    LazyColumn(
        contentPadding = PaddingValues(4.dp), modifier = modifier.padding(top = 15.dp, bottom = 120.dp),
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(160.dp),
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(person.posterUrl).crossfade(true).build(),
                    contentDescription = "poster",
                    contentScale = ContentScale.FillBounds,
                    alpha = 0.8f
                )

                Column(

                ) {
                    Text(
                        text = person.nameRu ?: "",
                        lineHeight = 25.sp,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )

                    Text(
                        text = person.profession ?: "",
                        lineHeight = 18.sp,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )
                    Text(
                        text = "${person.age} ${person.birthday}",
                        lineHeight = 18.sp,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )
                    Text(
                        text = person.birthplace ?: "",
                        lineHeight = 18.sp,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )
                }
            }
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
    Column (
       modifier =  Modifier.clickable { onMovieClick(movie.filmId) },
    ){
        HorizontalDivider()
        Text(text = movie.nameRu.toString())
        Text(text = movie.description.toString())
    }
}
