package com.example.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shu.models.collections.Collections
import com.shu.profile.R

@Composable
fun ProfileScreen(
    modifier: Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onCreateClick: () -> Unit,
    onMovieClick: (Int?) -> Unit,
) {

    val collection by viewModel.uiProfile.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
    ) {

        item {
            LazyRowMovie(
                list = collection.interesting,
                type = "Просмотренные",
                onMovieClick = onMovieClick
            )
        }
        item {
            Button(
                modifier = Modifier
                    .height(36.dp)
                    .fillMaxWidth(),
                onClick = { onCreateClick() },
            ) {
                Text(
                    stringResource(id = R.string.addCoollection),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            LazyRow(
                modifier = Modifier.padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(collection.collections.size) { num ->
                    Item(
                        collection = collection.collections[num],
                        onClick = {
//TODO open screen
                        }
                    )
                }
            }
        }

        item {
            LazyRowMovie(
                list = collection.interesting,
                type = "Вам было интересно",
                onMovieClick = onMovieClick
            )
        }
    }
}

@Composable
fun Item(
    collection: Collections, onClick: (Int) -> Unit
) {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(4.dp)
            .width(150.dp)
            .height(150.dp)
            .clickable { onClick(collection.collectionId) },

        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {

            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Localized description",
            )

            Text(
                text = collection.name.toString(),
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = collection.total.toString(),
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}