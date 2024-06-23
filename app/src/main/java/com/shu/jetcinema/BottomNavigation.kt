package com.shu.jetcinema

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNav(navController: NavHostController, items: List<NavigationScreens>) {
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
               // label = { Text(text = screen.label) },
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

/*


 Process: com.shu.jetcinema, PID: 23199
 java.lang.IllegalArgumentException: Navigation destination that matches request NavDeepLinkRequest{ uri=android-app://androidx.navigation/person_screen } cannot be found in the navigation graph ComposeNavGraph(0x0) startDestination={Destination(0x26775c05) route=main_screen}
 	at androidx.navigation.NavController.navigate(NavController.kt:1819)
 	at androidx.navigation.NavController.navigate(NavController.kt:2225)
 	at androidx.navigation.NavController.navigate$default(NavController.kt:2220)
 	at androidx.navigation.NavController.navigate(NavController.kt:2205)
 	at com.shu.jetcinema.BottomNavigationKt$BottomNav$1$1$1.invoke(BottomNavigation.kt:30)
 	at com.shu.jetcinema.BottomNavigationKt$BottomNav$1$1$1.invoke(BottomNavigation.kt:19)
 */