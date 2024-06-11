package com.shu.jetcinema

import android.util.Log
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNav(navController: NavHostController, items: List<BottomNavigationScreens>) {
    BottomAppBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = "icon for navigation item"
                    )
                },
                label = { Text(text = screen.label) },
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(screen.route) { inclusive = true }
                        }
                    }
                  /*  Log.d("navigation", "currentRoute $currentRoute")
                    when (currentRoute) {
                        "main_screen" -> {
                            when (screen.route) {
                                "main_screen" -> {
                                    navController.navigate(screen.route) {
                                        popUpTo(screen.route) { inclusive = true }
                                    }
                                }
                                else -> {}
                            }
                        }
                        "detail_screen" -> {
                            when (screen.route) {
                                "main_screen" -> {
                                    navController.navigate(screen.route) {
                                        popUpTo(screen.route) { inclusive = true }
                                    }
                                }
                                else -> {}
                            }
                        }
                        "list_screen" -> {
                            when (screen.route) {
                                "main_screen" -> {
                                    navController.navigate(screen.route) {
                                        popUpTo(screen.route) { inclusive = true }
                                    }
                                }
                                else -> {}
                            }
                        }

                        else -> {}
                    }*/
                }
            )
        }
    }
}
