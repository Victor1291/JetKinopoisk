package com.shu.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shu.models.ListCinema


@Composable
fun ListHours(
    list: ListCinema,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    // onCharacterClicked: (ListItem) -> Unit
) {
    Column(
        modifier = Modifier
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        /* Text(
             text = "${weather.location?.name ?: "no name"} size = ${weather.forecast?.forecastday?.first()?.hour?.size ?: 0}",
             color = MaterialTheme.colorScheme.primary
         )*/
        LazyRow(
            contentPadding = PaddingValues(4.dp),
            modifier = modifier,
            state = state
        ) {

            list.let { listHours ->
                listHours.items.size?.let {
                    items(it) { day ->
                        HoursCard(listHours.items[day])
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}


