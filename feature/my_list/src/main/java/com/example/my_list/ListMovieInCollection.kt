package com.example.my_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.design_system.component.MovieItemCard
import com.example.design_system.component.TopBar
import com.shu.models.CinemaItem

@Composable
fun ListMovieInCollection(
    list: List<CinemaItem>,
    name: String,
    navController: NavHostController,
    innerPadding: PaddingValues,
    onMovieClick: (Int?) -> Unit
) {

    Column(
        //  modifier = modifier
    ) {

        TopBar(
            header = name,
            leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            onLeftIconClick = { navController.navigateUp() },
        )

        Box(
            Modifier
                .fillMaxSize()

        ) {
            LazyVerticalGrid(
                // modifier = Modifier.padding(bottom = 80.dp),
                columns = GridCells.Adaptive(150.dp),
                contentPadding = innerPadding,
            ) {

                items(list.size) { index ->
                    val item = list[index]
                    MovieItemCard(item, onMovieClick = onMovieClick)
                    //  Text("Index=$index: $item", fontSize = 20.sp)
                }
            }
        }
    }

}