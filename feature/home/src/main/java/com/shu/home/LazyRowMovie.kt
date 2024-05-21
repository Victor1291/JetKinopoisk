package com.shu.home

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


@Composable
fun LazyRowMovie(
    list: List<CinemaItem>,
    num: Int,
    onMovieClick: (Int?) -> Unit,
    listName: List<String> = listOf("Premiers","Popular","Top 250","Third","Four","Five"),
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    ) {

    Column(
        modifier = Modifier
    ) {
         Text(
             text = listName[num],
             color = MaterialTheme.colorScheme.primary
         )
        LazyRow(
            contentPadding = PaddingValues(4.dp),
            modifier = modifier,
            state = state
        ) {

            list.let { listHours ->
                listHours.size?.let {
                    items(it) { day ->
                        MovieItemCard(listHours[day],onMovieClick = onMovieClick)
                    }
                }
            }
        }
    }
}


