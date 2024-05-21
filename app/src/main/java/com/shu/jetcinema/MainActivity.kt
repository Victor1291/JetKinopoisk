package com.shu.jetcinema

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.shu.jetcinema.ui.theme.JetCinemaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            JetCinemaTheme {
                val navController = rememberNavController()
                val viewModel: MainViewModel = hiltViewModel()
                val stateTopBar by viewModel.stateTOpBar.collectAsState()
                val bottomNavigationItems = listOf(
                    BottomNavigationScreens.MainScreen,
                    BottomNavigationScreens.SearchScreen,
                    BottomNavigationScreens.PersonScreen,
                )
                Scaffold(
                    topBar = {
                        if (stateTopBar) {
                            TopBar(
                                header = "CinemaWorld",
                            )
                        } else {
                            TopBar(
                                header = "",
                                leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                onLeftIconClick = { navController.navigateUp() },
                            )
                        }
                    },
                    content = { MainNavHost(navController, it, viewModel) },
                    bottomBar = {
                        BottomNav(navController, bottomNavigationItems)
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetCinemaTheme {
        Greeting("Android")
    }
}