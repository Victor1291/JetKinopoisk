package com.example.bottom_sheet

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shu.models.collections.Collections
import com.shu.models.details.DetailMovie

@Composable
fun BottomSheetScreen(
    film: DetailMovie,
    viewModel: BottomSheetViewModel = hiltViewModel(),
    onCreateClick: () -> Unit

) {

    val collection by viewModel.uiCollections.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.movieId = film.kinopoiskId
        viewModel.refresh()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_16)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {

            AsyncImage(
                modifier = Modifier
                    .width(120.dp)
                    .height(170.dp),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(film.posterUrlPreview).build(),
                contentDescription = "picture",
                contentScale = ContentScale.FillBounds,
            )

            Text(
                text = film.nameRu.toString(),
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 4.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }

        LazyColumn(
            modifier = Modifier.padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(collection.size) { num ->
                collection[num].let { collection ->
                    BottomSheetItem(
                        collection = collection,
                        onClick = {
                            Log.d("isChecked begin", "${collection.checked}")
                            collection.checked = !collection.checked
                            if (collection.checked) {
                                Log.d("true checked", "true")
                                viewModel.addMovieInCollection(num + 1)
                            } else {
                                Log.d("false checked", "false")
                                viewModel.removeMovieInCollection(num + 1)
                            }
                        }
                    )
                }
            }
            item {
                Button(
                    modifier = Modifier
                        .height(36.dp)
                        .fillMaxWidth(),
                    enabled = true,
                    onClick = {
                        onCreateClick()
                        //TODO обработать успешность создания коллекции
                        //viewModel.setNewCollection()
                              },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        stringResource(id = R.string.addCoollection),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomSheetItem(
    collection: Collections, onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        IconToggleButton(checked = collection.checked, onCheckedChange = {}) {
            val tint by animateColorAsState(
                if (collection.checked) Color(0xFFEC407A) else Color(
                    0xFFB0BEC5
                ), label = ""
            )
            Icon(Icons.Filled.Favorite, contentDescription = "Localized description", tint = tint)
        }
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