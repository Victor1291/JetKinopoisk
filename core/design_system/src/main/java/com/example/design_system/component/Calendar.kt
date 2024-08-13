package com.example.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.text.InternalFoundationTextApi
import androidx.compose.foundation.text.TextDelegate
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.sp

@OptIn(InternalFoundationTextApi::class)
@Preview
@Composable
fun StartCalendar() {
    val start = 2000
    val end = 2019
    val select = 2011
    val year = start..end
    val list = mutableListOf<String>()
    list.add("Искать в период с")
    list.add("$start - $end")
    list.add(" < ")
    list.add(" > ")
    year.forEach {
        list.add(it.toString())
    }

    Excell {
        list.forEachIndexed() { index, item ->

            when (index) {

                0 -> {
                    TextDelegate(
                        //   modifier = Modifier.padding(vertical = 8.dp),
                        text = AnnotatedString(text = item),
                        density = Density(40f),
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamilyResolver = LocalFontFamilyResolver.current
                    )
                }

                else -> {

                    Button(
                        modifier = Modifier.background(color = if (item == select.toString()) Color.White else Color.Black),
                        onClick = {},
                    ) {
                        Text(
                            text = item,
                            fontSize = 12.sp,
                            color = Color.Green
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Excell(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        layout(constraints.maxWidth, constraints.maxHeight) {
            var x = 20
            var column = 5
            var y = 20
            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(x = x, y = y)
                when (index) {
                    0 -> y += placeable.height
                    1 -> x += placeable.width + 230
                    2 -> x += placeable.width + 40
                    3 -> {
                        x = 20
                        y += placeable.height
                    }

                    else -> {
                        if (index == 3 + column) {
                            x = 20
                            y += placeable.height
                            column += 5
                        } else {
                            x += placeable.width
                        }
                    }
                }
            }
        }
    }
}
