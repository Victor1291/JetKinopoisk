package com.shu.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shu.models.CinemaItem
import com.shu.models.ETitle
import com.shu.models.FilmVip


@Composable
fun LazyRowMovie(
    list: List<CinemaItem>,
    num: Int,
    onMovieClick: (Int?) -> Unit,
    listName: List<ETitle> = listOf(
        ETitle.Premieres,
        ETitle.Popular,
        ETitle.Top250,
        ETitle.FilmVip,
        ETitle.FilmVip,
        ETitle.SerialVip
    ),
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    onListClick: (FilmVip?) -> Unit,
) {

    Column(
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 4.dp, top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = listName[num].name,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
            )
            //TODO добавить стрелку иконку
            Text(
                text = "All",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                modifier = Modifier
                    .clickable { onListClick(FilmVip(title = listName[num])) },
                //TODO реализовать передачу коллекций Vip по клику
            )
        }

        LazyRow(
            contentPadding = PaddingValues(4.dp),
            modifier = modifier,
            state = state
        ) {

            list.let { listHours ->
                listHours.size?.let {
                    items(it) { day ->
                        MovieItemCard(listHours[day], onMovieClick = onMovieClick)
                    }
                }
            }
        }
    }
}


