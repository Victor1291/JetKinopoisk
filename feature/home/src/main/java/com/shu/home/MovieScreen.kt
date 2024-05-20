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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = " Kinopoisk ",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        LazyColumn(
            contentPadding = PaddingValues(4.dp),
            modifier = modifier.padding(top = 20.dp,bottom = 60.dp),
            state = state
        ) {

            items(manyScreens.homeListScreen.size) { num ->

                ListHours(list = manyScreens.homeListScreen[num],num)

            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}







