package com.shu.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shu.models.ManyScreens


@Composable
fun MovieScreen(
    manyScreens: ManyScreens,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    // onCityClicked: (String?) -> Unit
) {

    Column(
        modifier = Modifier
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = " Kinopoisk ",
            color = MaterialTheme.colorScheme.primary
        )
        LazyColumn(
            contentPadding = PaddingValues(4.dp),
            modifier = modifier,
            state = state
        ) {

            items(manyScreens.homeListScreen.size) { num ->

                ListHours(list = manyScreens.homeListScreen[num])

            }
        }
    }
    Spacer(modifier = Modifier.height(100.dp))
}







