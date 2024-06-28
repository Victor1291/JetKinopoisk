package com.shu.jetcinema

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.design_system.theme.JetCinemaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
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
                Scaffold(
                    content = { padding ->
                        MainNavHost(
                            navController = navController,
                            paddingValues = padding,
                            viewModel = viewModel,
                            sheetState = sheetState,
                        )
                    },
                    bottomBar = {
                        BottomNav(navController, bottomNavigationItems)
                    }
                )
            }
        }
    }
}



