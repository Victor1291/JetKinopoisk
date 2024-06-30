package com.shu.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.design_system.component.MovieItemCard
import com.example.design_system.component.RowTwoText
import com.shu.models.CinemaItem
import com.shu.models.ETitle
import com.shu.models.FilmVip

@Composable
fun LazyRowMovie(
    modifier: Modifier = Modifier,
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
    state: LazyListState = rememberLazyListState(),
    onListClick: (FilmVip?) -> Unit,
) {

    Column(
        modifier = Modifier
    ) {

        RowTwoText(
            first = listName[num].name,
            second = "ALL",
            onClick = { onListClick(FilmVip(title = listName[num])) })

        LazyRow(
            contentPadding = PaddingValues(4.dp), modifier = modifier, state = state
        ) {
            items(list) { movie ->
                MovieItemCard(movie, onMovieClick = onMovieClick)
            }
        }
    }
}


