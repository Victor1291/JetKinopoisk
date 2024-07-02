package com.shu.jetcinema

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Composable
fun BottomNav(navController: NavHostController, items: List<NavigationScreens>) {
    BottomAppBar(
        contentPadding = PaddingValues(top = 2.dp, bottom = 2.dp),
        windowInsets = BottomAppBarDefaults.windowInsets
    ) {
        // Leading icons should typically have a high content alpha
        /* CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
             IconButton(onClick = { *//* doSomething() *//* }) {
                Icon(Icons.Filled.Menu, contentDescription = "Localized description")
            }
        }*/

        items.forEach { screen ->
            Spacer(Modifier.weight(1f, true))
            IconButton(onClick = {

                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }


            }) {
                Icon(screen.icon, contentDescription =stringResource(screen.label))
            }

        }
        Spacer(Modifier.weight(1f, true))
    }
}