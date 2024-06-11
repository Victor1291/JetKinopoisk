package com.shu.jetcinema

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationScreens(val route: String, val label: String, val icon: ImageVector) {
    data object MainScreen : BottomNavigationScreens(
        route = "main_screen",
        label = "Home",
        icon = Icons.Default.Home
    )

    data object SearchScreen : BottomNavigationScreens(
        route = "location_screen",
        label = "Поиск",
        icon = Icons.Default.Search
    )

    data object PersonScreen : BottomNavigationScreens(
        route = "location_screen",
        label = "Профиль",
        icon = Icons.Default.Person
    )

    data object DetailScreen : BottomNavigationScreens(
        route = "detail_screen",
        label = "Movie",
        icon = Icons.Default.Menu
    )

    data object ListMovies : BottomNavigationScreens(
        route = "list_screen",
        label = "Movies",
        icon = Icons.Default.Menu
    )

}