package com.shu.detail_movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinema_elements.RowTwoText
import com.shu.models.details.Actor

@Composable
fun GridActors(
    actors: List<Actor>,
    onActorClick: (Int?) -> Unit,
    onAllClick: () -> Unit,
) {
    Column(
        modifier = Modifier
    ) {

        RowTwoText(first = "В фильме участвовали", two = "All", onClick = { onAllClick() })

        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier.height(300.dp),
            contentPadding = PaddingValues(4.dp),
            state = LazyGridState()
        ) {
            items(actors.size) { number ->
                ActorItem(actors[number], onActorClick)
            }
        }
    }
}