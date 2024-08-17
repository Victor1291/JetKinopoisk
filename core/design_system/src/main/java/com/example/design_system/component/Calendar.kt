package com.example.design_system.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed

/**
 * При смене конфигурации сделать другой лист горизонтальный.
 * последний год в таблице должен быть текущим.
 * не увеличивать больше текущего года.
 */
@Preview
@Composable
fun NewCalendar() {
    //Первая таблица
    val start = remember { mutableIntStateOf(2005) }
    val end = remember { mutableIntStateOf(2024) }
    val select = remember { mutableIntStateOf(13) }
    val year = start.intValue..end.intValue
    val list = mutableListOf<String>()
    list.add("Искать в период с")
    list.add("${start.intValue} - ${end.intValue}")
    list.add(" < ")
    list.add(" > ")
    year.forEach {
        list.add(it.toString())
    }
    //вторая таблица
    val start2 = remember { mutableIntStateOf(2005) }
    val end2 = remember { mutableIntStateOf(2024) }
    val select2 = remember { mutableIntStateOf(37) }
    val year2 = start2.intValue..end2.intValue
    list.add("Искать в период до")
    list.add("${start2.intValue} - ${end2.intValue}")
    list.add(" < ")
    list.add(" > ")
    year2.forEach {
        list.add(it.toString())
    }
    list.add(" ВЫБРАТЬ ")


    StartCalendar(
        select1 = select.intValue, select2 = select2.intValue, list = list,
        onSelect1 = {
            Log.d("excell", "select1 = $it ")
            select.intValue = it
        },
        onSelect2 = {
            Log.d("excell", "select2 = $it ")
            select2.intValue = it
        },
        onLeft1 = {
            start.intValue -= 10
            end.intValue -= 10
        },
        onLeft2 = {
            if (end.intValue < end2.intValue) {
                start2.intValue -= 10
                end2.intValue -= 10
            }
        },
        onRight1 = {
            if (end2.intValue > end.intValue) {
                start.intValue += 10
                end.intValue += 10
            }
        },
        onRight2 = {
            start2.intValue += 10
            end2.intValue += 10
        },
        onSelect = {

        }
    )
}

@Composable
fun StartCalendar(
    select1: Int,
    select2: Int,
    list: List<String>,
    onSelect1: (Int) -> Unit,
    onSelect2: (Int) -> Unit,
    onLeft1: () -> Unit,
    onLeft2: () -> Unit,
    onRight1: () -> Unit,
    onRight2: () -> Unit,
    onSelect: () -> Unit,
) {
    Excell {
        list.forEachIndexed { index, item ->

            when (index) {

                0, 24 -> {
                    Text(
                        text = item, color = Color.Black, fontSize = 14.sp
                    )
                }

                select1, select2 -> {

                    Button(
                        modifier = Modifier.background(color = Color.White),
                        onClick = {},
                        colors = ButtonColors(
                            containerColor = Color.Black,
                            contentColor = Color.Red,
                            disabledContainerColor = Color.White,
                            disabledContentColor = Color.LightGray
                        ),
                        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = item, color = Color.Green, fontSize = 12.sp,
                        )
                    }
                }

                1 -> {

                    Button(
                        modifier = Modifier.background(color = Color.White),
                        onClick = { },
                        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = item, color = Color.Green, fontSize = 12.sp,
                        )
                    }
                }

                2 -> {

                    Button(
                        modifier = Modifier.background(color = Color.White),
                        onClick = { onLeft1() },
                        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = item, color = Color.Green, fontSize = 12.sp,
                        )
                    }
                }

                3 -> {

                    Button(
                        modifier = Modifier.background(color = Color.White),
                        onClick = { onRight1() },
                        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = item, color = Color.Green, fontSize = 12.sp,
                        )
                    }
                }


                4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 -> {

                    Button(
                        modifier = Modifier.background(color = Color.White),
                        onClick = { onSelect1(index) },
                        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = item, color = Color.Green, fontSize = 12.sp,
                        )
                    }
                }

                25 -> {

                    Button(
                        modifier = Modifier.background(color = Color.White),
                        onClick = { },
                        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = item, color = Color.Green, fontSize = 12.sp,
                        )
                    }
                }

                26 -> {

                    Button(
                        modifier = Modifier.background(color = Color.White),
                        onClick = { onLeft2() },
                        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = item, color = Color.Green, fontSize = 12.sp,
                        )
                    }
                }

                27 -> {

                    Button(
                        modifier = Modifier.background(color = Color.White),
                        onClick = { onRight2() },
                        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = item, color = Color.Green, fontSize = 12.sp,
                        )
                    }
                }

                28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47 -> {

                    Button(
                        modifier = Modifier.background(color = Color.White),
                        onClick = { onSelect2(index) },
                        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = item, color = Color.Green, fontSize = 12.sp,
                        )
                    }
                }

                48 -> {

                    Button(
                        modifier = Modifier.background(color = Color.White),
                        onClick = { onSelect() },
                        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = item, color = Color.Green, fontSize = 14.sp,
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun Excell(
    modifier: Modifier = Modifier, content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier, content = content
    ) { measurables, constraints ->

        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        layout(constraints.maxWidth, constraints.constrainHeight(1600)) {
            var x = 20
            var column = 5
            var y = 20
            placeables.fastForEachIndexed { index, placeable ->
                placeable.placeRelative(x = x, y = y)
                when (index) {
                    0, 24 -> y += placeable.height + 20
                    1, 25 -> x += placeable.width + 230
                    2, 26 -> x += placeable.width + 40
                    3 -> {
                        x = 20
                        y += placeable.height
                    }

                    27 -> {
                        x = 20
                        y += placeable.height
                        column = 29
                    }

                    47 -> {
                        x = 325
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