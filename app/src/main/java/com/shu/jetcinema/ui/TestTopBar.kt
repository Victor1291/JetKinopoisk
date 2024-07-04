package com.shu.jetcinema.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.room.util.query
import com.example.search.components.Utils
import com.shu.mylibrary.R
import kotlin.math.roundToInt
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigationIcon: @Composable () -> Unit,
    actions: @Composable () -> Unit,
) {

    val context = LocalContext.current.applicationContext
    val isActive = remember {
        mutableStateOf(false)
    }
    val mainSearch = remember {
        mutableStateOf("news")
    }
    val topBarHeight = 50.dp
    val topBarHeightPx = with(LocalDensity.current) { topBarHeight.roundToPx().toFloat() }
    val topBarOffsetHeightPx = remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset, source: NestedScrollSource
            ): Offset {

                val delta = available.y
                Log.d("delta", " $delta")
                val newOffset =
                    topBarOffsetHeightPx.floatValue - delta //меняем снаправление + или -
                topBarOffsetHeightPx.floatValue = newOffset.coerceIn(0f, topBarHeightPx)

                return Offset.Zero
            }
        }
    }

    Scaffold(modifier = Modifier.nestedScroll(nestedScrollConnection),


        topBar = {

            DockedSearchBar(
                query = mainSearch.value,
                onQueryChange = { text ->
                    mainSearch.value = text
                },
                onSearch = { text ->
                    isActive.value = false
                    Toast.makeText(context, "Search $text ", Toast.LENGTH_LONG).show()
                },
                placeholder = {
                    Text(
                        text = "new"
                    )
                },
                active = isActive.value,
                onActiveChange = {
                    isActive.value = it
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.PlayArrow,
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(48.dp)
                            .clickable { },
                        contentDescription = stringResource(R.string.personsearch)
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Rounded.Settings,
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(48.dp)
                            .clickable { isActive.value = false },
                        contentDescription = "ddd"
                    )
                },
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp,end = 8.dp)
                    .height(topBarHeight)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = -topBarOffsetHeightPx.floatValue.roundToInt()
                        )
                    }
            ) {

            }

        }
    ) { innerPaing ->

        LazyColumn(contentPadding = innerPaing) {
            items(count = 100) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(
                            Color(
                                Random.nextInt(1, 200),
                                Random.nextInt(1, 200),
                                Random.nextInt(1, 200)
                            )
                        )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navigationIcon: @Composable () -> Unit,
    actions: @Composable () -> Unit,
) {
    val topBarHeight = 50.dp
    val topBarHeightPx = with(LocalDensity.current) { topBarHeight.roundToPx().toFloat() }
    val topBarOffsetHeightPx = remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset, source: NestedScrollSource
            ): Offset {

                val delta = available.y
                Log.d("delta", " $delta")
                val newOffset =
                    topBarOffsetHeightPx.floatValue - delta //меняем снаправление + или -
                topBarOffsetHeightPx.floatValue = newOffset.coerceIn(0f, topBarHeightPx)

                return Offset.Zero
            }
        }
    }

    Scaffold(modifier = Modifier.nestedScroll(nestedScrollConnection),


        topBar = {
            TopAppBar(
                title = { Text(text = "Settings Screen") },
                navigationIcon = { navigationIcon() },
                actions = { actions() },
                colors = topAppBarColors(
                    containerColor = Color.Green,
                    scrolledContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    navigationIconContentColor = Color.Red,
                    titleContentColor = Color.Blue,
                    actionIconContentColor = Color.Magenta
                ),
                modifier = Modifier
                    .height(topBarHeight)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = -topBarOffsetHeightPx.floatValue.roundToInt()
                        )
                    },
                windowInsets = TopAppBarDefaults.windowInsets
            )
        }) { innerPaing ->

        LazyColumn(contentPadding = innerPaing) {
            items(count = 100) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(
                            Color(
                                Random.nextInt(1, 200),
                                Random.nextInt(1, 200),
                                Random.nextInt(1, 200)
                            )
                        )
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen(navigationIcon = {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }
    }, actions = {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
        }
    })
}

@Preview
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen(navigationIcon = {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }
    }, actions = {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
        }
    })
}