package com.shu.jetcinema

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationScreens(val route: String, @StringRes val label: Int, val icon: ImageVector) {
    data object MainScreen : NavigationScreens(
        route = "main_screen",
        label = R.string.home,
        icon = Icons.Default.Home
    )

    data object PostsScreen : NavigationScreens(
        route = "postsscreen",
        label = R.string.posts,
        icon = Icons.Default.Warning
    )

    data object SearchScreen : NavigationScreens(
        route = "search_screen",
        label = R.string.search,
        icon = Icons.Default.Search
    )

    data object PersonScreen : NavigationScreens(
        route = "person_screen",
        label = R.string.person,
        icon = Icons.Default.Person
    )

    data object DetailScreen : NavigationScreens(
        route = "detail_screen",
        label = R.string.movie,
        icon = Icons.Default.Menu
    )

    data object ListMovies : NavigationScreens(
        route = "list_screen",
        label = R.string.movies,
        icon = Icons.Default.Menu
    )

    data object BottomDialog : NavigationScreens(
        route = "bottom_screen",
        label = R.string.collections,
        icon = Icons.Default.Menu
    )

    data object YearDialog : NavigationScreens(
        route = "year_screen",
        label = R.string.collections,
        icon = Icons.Default.Menu
    )

    data object ProfileScreen : NavigationScreens(
        route = "profile_screen",
        label = R.string.profile,
        icon = Icons.Default.Person
    )
data object MyListScreen : NavigationScreens(
        route = "my_list_screen",
        label = R.string.profile,
        icon = Icons.Default.Person
    )

    data object GalleryScreen : NavigationScreens(
        route = "gallery_screen",
        label = R.string.gallery,
        icon = Icons.Default.Person
    )

    data object FilterScreen : NavigationScreens(
        route = "filter_screen",
        label = R.string.filter,
        icon = Icons.Default.Person
    )
}