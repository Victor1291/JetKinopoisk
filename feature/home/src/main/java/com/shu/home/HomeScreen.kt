package com.shu.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shu.models.ManyScreens


@Composable
fun HomeScreen(
    manyScreens: ManyScreens,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    onMovieClick: (Int?) -> Unit
) {

    Column(
        modifier = Modifier
    ) {
        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn(
            contentPadding = PaddingValues(4.dp),
            modifier = modifier.
            padding(top = 10.dp,bottom = 110.dp),
            state = state
        ) {

            items(manyScreens.homeListScreen.size) { num ->

                LazyRowMovie(list = manyScreens.homeListScreen[num],num, onMovieClick = onMovieClick)

            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}







