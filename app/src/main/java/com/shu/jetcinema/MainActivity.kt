package com.shu.jetcinema

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.design_system.component.TopBar
import com.example.design_system.theme.JetCinemaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            JetCinemaTheme {
                val navController = rememberNavController()
                val sheetState = rememberModalBottomSheetState()
                val viewModel: MainViewModel = hiltViewModel()
                //val stateTopBar by viewModel.stateTOpBar.collectAsState()
                val bottomNavigationItems = listOf(
                    NavigationScreens.MainScreen,
                    NavigationScreens.SearchScreen,
                    NavigationScreens.ProfileScreen,
                )
                val snackbarHostState = remember { SnackbarHostState() }

                val bottomBarHeight = 100.dp
                val bottomBarHeightPx =
                    with(LocalDensity.current) { bottomBarHeight.roundToPx().toFloat() }
                val bottomBarOffsetHeightPx = remember { mutableFloatStateOf(0f) }


// connection to the nested scroll system and listen to the scroll
// happening inside child LazyColumn
                val nestedScrollConnection = remember {
                    object : NestedScrollConnection {
                        override fun onPreScroll(
                            available: Offset,
                            source: NestedScrollSource
                        ): Offset {

                            val delta = available.y
                            val newOffset = bottomBarOffsetHeightPx.floatValue + delta
                            bottomBarOffsetHeightPx.floatValue =
                                newOffset.coerceIn(-bottomBarHeightPx, 0f)

                            return Offset.Zero
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.nestedScroll(nestedScrollConnection),

                    bottomBar = {
                        BottomAppBar(
                            modifier = Modifier
                                .height(bottomBarHeight)
                                .offset {
                                    IntOffset(
                                        x = 0,
                                        y = -bottomBarOffsetHeightPx.floatValue.roundToInt()
                                    )
                                },
                            contentPadding = PaddingValues(top = 2.dp, bottom = 2.dp),
                            windowInsets = BottomAppBarDefaults.windowInsets
                        ) {
                            bottomNavigationItems.forEach { screen ->
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
                                    Icon(screen.icon, contentDescription = "Localized description")
                                }

                            }
                            Spacer(Modifier.weight(1f, true))
                        }
                    },
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    content =
                    { innerPadding ->

                        MainNavHost(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .consumeWindowInsets(innerPadding)
                                .windowInsetsPadding(
                                    WindowInsets.safeDrawing.only(
                                        WindowInsetsSides.Horizontal,
                                    ),
                                ),
                            navController = navController,
                            viewModel = viewModel,
                            sheetState = sheetState,
                            innerPadding = innerPadding,
                            onShowSnackbar = { message, action ->
                                snackbarHostState.showSnackbar(
                                    message = message,
                                    actionLabel = action,
                                    duration = SnackbarDuration.Short,
                                ) == SnackbarResult.ActionPerformed
                            },
                        )
                    },
                )
            }
        }
    }
}

/* For Testing hide BottomNavigation

LazyColumn(contentPadding = innerPaing) {
                              items(count = 100) {
                                  Box(
                                      Modifier
                                          .fillMaxWidth()
                                          .height(50.dp)
                                          .background(Color(Random.nextInt(1,200),Random.nextInt(1,200),Random.nextInt(1,200))))
                              }
                          }
                      },


 */

