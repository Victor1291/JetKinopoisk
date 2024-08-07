package com.example.gallery

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.design_system.component.NewRowTabs
import com.shu.models.gallery_models.GalleryItem
import kotlinx.coroutines.flow.Flow
import kotlin.math.roundToInt

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GalleryScreen(
    modifier: Modifier,
    galleryList: Flow<PagingData<GalleryItem>>,
    countList: List<Int>,
    viewModel: GalleryViewModel,
    list: List<String> = listOf(
        "STILL",
        "SCREENSHOT",
        "SHOOTING",
        "FAN_ART",
        "WALLPAPER",
        "PROMO",
        "POSTER",
        "CONCEPT",
        "COVER",

        ),
    onBackClick: () -> Unit,
) {

    val lazyPagingItems = galleryList.collectAsLazyPagingItems()

    val swipeRefreshState =
        rememberPullRefreshState(false, onRefresh = { lazyPagingItems.refresh() })

    val select = remember {
        mutableIntStateOf(viewModel.select)
    }
    val cellConfiguration = if (LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE) {
        StaggeredGridCells.Adaptive(minSize = 175.dp)
    } else StaggeredGridCells.Fixed(1)

    var expanded by remember { mutableStateOf(false) }
    var keyScroll by remember { mutableStateOf(true) }

    //Hide Scroll
    val topBarHeight = 70.dp
    val topBarHeightPx = with(LocalDensity.current) { topBarHeight.roundToPx().toFloat() }
    val topBarOffsetHeightPx = remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset, source: NestedScrollSource
            ): Offset {

                val delta = available.y
                // Log.d("delta", " $delta")
                val newOffset =
                    topBarOffsetHeightPx.floatValue - delta //меняем снаправление + или -
                topBarOffsetHeightPx.floatValue = newOffset.coerceIn(0f, topBarHeightPx)

                return Offset.Zero
            }
        }
    }

    val state = rememberLazyStaggeredGridState()
    Scaffold(modifier = Modifier.nestedScroll(nestedScrollConnection),


        topBar = {

            NewRowTabs(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .height(topBarHeight)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = -topBarOffsetHeightPx.floatValue.roundToInt()
                        )
                    },
                selected = select.intValue,
                countList = countList,
                list = list,
                onClick = { index ->
                    select.intValue = index
                    if (viewModel.type != list[index]) {
                        //сохраняем позицию предыдущей категории.
                        viewModel.indexType[viewModel.type] = state.firstVisibleItemIndex
                        //даём добро на скроллинг
                        keyScroll = true
                        viewModel.type = list[index]
                        viewModel.select = index
                        viewModel.getGallery()
                    }
                },
            )

        }) { innerPaing ->

        //Scroll to previous position

        LaunchedEffect(key1 = keyScroll) {
            viewModel.indexType[viewModel.type]?.let { state.scrollToItem(it) }
            keyScroll = false
        }

        Box(
            Modifier
                .fillMaxSize()
                .pullRefresh(swipeRefreshState)
        ) {
            LazyVerticalStaggeredGrid(
                columns = cellConfiguration,
                contentPadding = innerPaing,
                state = state
            ) {

                if (lazyPagingItems.itemCount != 0) {
                    expanded = false
                    items(count = lazyPagingItems.itemCount) { index ->
                        val item = lazyPagingItems[index]
                        if (item != null) {
                            GalleryItemCard(item, onMovieClick = {})
                        }
                    }
                } else {
                    expanded = true
                }
            }
            if (expanded) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        text = "No picture. Sorry...",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                    )
                }
            }
            if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        text = "Loading...",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
            PullRefreshIndicator(
                lazyPagingItems.loadState.append == LoadState.Loading,
                swipeRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

