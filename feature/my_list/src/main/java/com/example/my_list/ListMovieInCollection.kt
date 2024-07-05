package com.example.my_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.design_system.component.MovieItemCard
import com.example.design_system.component.TopBar

@Composable
fun ListMovieInCollection(
    modifier: Modifier,
    innerPadding: PaddingValues,
    name: String,
    viewModel: MyListViewModel = hiltViewModel(),
    navController: NavHostController,
    onMovieClick: (Int?) -> Unit,
) {

    val listMovie by viewModel.listMovie.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.refresh(name)
    }

    Column(
        //  modifier = modifier
    ) {

        TopBar(
            header = name,
            leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            onLeftIconClick = { navController.navigateUp() },
            rightIconImageVector = Icons.Default.Delete,
            onRightIconClick = {  viewModel.clearCollection(name)  },
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

                items(listMovie.size) { index ->
                    val item = listMovie[index]
                    MovieItemCard(item, onMovieClick = onMovieClick)
                    //  Text("Index=$index: $item", fontSize = 20.sp)
                }
            }
        }
    }

}