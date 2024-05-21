package com.shu.detail_movie.state

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.verticalNegativePadding

@Preview
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {


    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "LOADING...",
            textAlign = TextAlign.Center,
            modifier = modifier
                .requiredHeight(40.dp)
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        0.0f to Color.Red,
                        0.3f to Color.Green,
                        1.0f to Color.Blue,
                        start = Offset(0.0f, 50.0f),
                        end = Offset(0.0f, 100.0f)
                    ), shape = RectangleShape, alpha = 0.5F
                )
                .verticalNegativePadding(2.dp),
            fontSize = 15.sp,
            color = Color.White,
        )
    }
}
