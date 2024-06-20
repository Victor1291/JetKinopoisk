package com.example.bottom_sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shu.models.details.DetailMovie
import com.shu.models.collections.Collections

@Composable
fun BottomSheetScreen(
    film: DetailMovie,
    viewModel: BottomSheetViewModel = hiltViewModel(),
    onClick: () -> Unit

) {

    val collection by viewModel.uiCollections.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {

                AsyncImage(
                    modifier = Modifier
                        .width(180.dp)
                        .height(270.dp),
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
                collection[num]?.let {
                    BottomSheetItem(
                        collection = it
                    ) { } //TODO

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
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
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