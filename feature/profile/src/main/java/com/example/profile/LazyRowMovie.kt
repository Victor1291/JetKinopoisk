package com.example.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.design_system.component.ClearItemCard
import com.example.design_system.component.MovieItemCard
import com.example.design_system.component.RowTwoText
import com.shu.models.CinemaItem

@Composable
fun LazyRowMovie(
    list: List<CinemaItem>,
    type: String,
    onMovieClick: (Int?) -> Unit,
    onAllClick: (List<CinemaItem>) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
) {
    Column(
        modifier = Modifier
    ) {

        RowTwoText(first = type, second = list.size.toString(), onClick = { onAllClick(list) })

        LazyRow(
            contentPadding = PaddingValues(4.dp),
            modifier = modifier,
            state = state
        ) {

            items(list.size) { id ->
                MovieItemCard(list[id], onMovieClick = onMovieClick)
            }

            item {
                ClearItemCard (type){
                    /*Todo clear*/
                }
            }
        }
    }
}


