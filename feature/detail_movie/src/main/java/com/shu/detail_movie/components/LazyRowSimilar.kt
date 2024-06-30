package com.shu.detail_movie.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.design_system.component.MovieItemCard
import com.example.design_system.component.RowTwoText
import com.shu.models.similar_models.ListSimilar
import com.shu.models.similar_models.toCinemaItem


@Composable
fun LazyRowSimilar(
    similar: ListSimilar,
    onMovieClick: (Int?) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    ) {
    Column(
        modifier = Modifier
    ) {
        RowTwoText(first = "Похожие",second = similar.total.toString())
        LazyRow(
            contentPadding = PaddingValues(4.dp),
            modifier = modifier,
            state = state
        ) {

            similar.items.let { listHours ->
                items(listHours.size) { id ->
                    MovieItemCard(similar.items[id].toCinemaItem(),onMovieClick = onMovieClick)
                }
            }
        }
    }
}


