package com.example.profile

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
fun LazyRowMovie(
    list: List<CinemaItem>,
    type: String,
    onMovieClick: (Int?) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    ) {
    Column(
        modifier = Modifier
    ) {
         Text(
             text = type,
             color = MaterialTheme.colorScheme.primary
         )
        LazyRow(
            contentPadding = PaddingValues(4.dp),
            modifier = modifier,
            state = state
        ) {

            list.let { listMovie ->
                listMovie.size?.let {
                    items(it) { id ->
                        MovieItemCard(list[id],onMovieClick = onMovieClick)
                    }
                }
            }
        }
    }
}


