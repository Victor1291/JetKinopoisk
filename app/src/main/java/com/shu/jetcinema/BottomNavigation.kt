package com.shu.jetcinema

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Composable
fun BottomNav(navController: NavHostController, items: List<NavigationScreens>) {
    BottomAppBar(
        contentPadding = PaddingValues(top = 4.dp, bottom = 4.dp),
        windowInsets = BottomAppBarDefaults.windowInsets
    ) {
        // Leading icons should typically have a high content alpha
       /* CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
            IconButton(onClick = { *//* doSomething() *//* }) {
                Icon(Icons.Filled.Menu, contentDescription = "Localized description")
            }
        }*/
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = {
            navController.navigate(NavigationScreens.MainScreen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }) {
            Icon(NavigationScreens.MainScreen.icon, contentDescription = "Localized description")
        }
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = {
            navController.navigate(NavigationScreens.SearchScreen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }) {
            Icon(NavigationScreens.SearchScreen.icon, contentDescription = "Localized description")
        }
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = {
            navController.navigate(NavigationScreens.ProfileScreen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }) {
            Icon(NavigationScreens.ProfileScreen.icon, contentDescription = "Localized description")
        }
        Spacer(Modifier.weight(1f, true))
    }
}


/*   BottomAppBar(
   ) {
       val navBackStackEntry by navController.currentBackStackEntryAsState()
       val currentRoute = navBackStackEntry?.destination?.route
       items.forEach { screen ->
           NavigationBarItem(
               selected = currentRoute == screen.route,
               icon = {
                   Icon(
                       modifier = Modifier.size(24.dp),
                       imageVector = screen.icon,
                       contentDescription = "icon for navigation item"
                   )
               },
               // label = { Text(text = stringResource(id = screen.label)) },
               onClick = {

                   navController.navigate(screen.route) {
                       // avoid building up a large stack of destinations
                       // on the back stack as users select items
                       popUpTo(navController.graph.findStartDestination().id) {
                           saveState = true
                       }
                       // Avoid multiple copies of the same destination when
                       // reselecting the same item
                       launchSingleTop = true
                       // Restore state when reselecting a previously selected item
                       restoreState = true
                   }
               }
           )
       }
   }
}*/
