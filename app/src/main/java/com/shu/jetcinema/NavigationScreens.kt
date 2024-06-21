package com.shu.jetcinema

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationScreens(val route: String, val label: String, val icon: ImageVector) {
    data object MainScreen : NavigationScreens(
        route = "main_screen",
        label = "Home",
        icon = Icons.Default.Home
    )

    data object SearchScreen : NavigationScreens(
        route = "search_screen",
        label = "Search",
        icon = Icons.Default.Search
    )

    data object PersonScreen : NavigationScreens(
        route = "person_screen",
        label = "Person",
        icon = Icons.Default.Person
    )

    data object DetailScreen : NavigationScreens(
        route = "detail_screen",
        label = "Movie",
        icon = Icons.Default.Menu
    )

    data object ListMovies : NavigationScreens(
        route = "list_screen",
        label = "Movies",
        icon = Icons.Default.Menu
    )

    data object BottomDialog : NavigationScreens(
        route = "bottom_screen",
        label = "Collections",
        icon = Icons.Default.Menu
    )

    data object ProfileScreen : NavigationScreens(
        route = "profile_screen",
        label = "Profile",
        icon = Icons.Default.Person
    )
    //TODO разделить на разные sealed class.
}