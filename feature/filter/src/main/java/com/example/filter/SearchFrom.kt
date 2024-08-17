package com.example.filter

import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.design_system.component.Excell

@Composable
fun SearchFrom(
    viewModel: FilterViewModel
) {

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
                    Text(
                        text = item,
                        color = Color.Green
                    )
                }

                2 -> {
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

                3 -> {
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