package com.shu.detail_movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shu.models.CinemaItem
import com.shu.models.similar_models.ListSimilar


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
         Text(
             text = "Похожии",
             color = MaterialTheme.colorScheme.primary
         )
        LazyRow(
            contentPadding = PaddingValues(4.dp),
            modifier = modifier,
            state = state
        ) {

            similar.items.let { listHours ->
                listHours.size?.let {
                    items(it) { id ->
                        MovieItemCard(similar.items[id],onMovieClick = onMovieClick)
                    }
                }
            }
        }
    }
}


