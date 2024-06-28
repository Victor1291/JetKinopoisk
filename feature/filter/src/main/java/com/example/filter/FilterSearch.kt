package com.example.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cinema_elements.RowTwoText
import com.example.cinema_elements.TopBar
import com.example.cinema_elements.RangeSlider
import com.example.cinema_elements.MaterialButtonToggleGroup

import com.shu.models.FilmVip

@Composable
fun FilterSearch(
    filmVip: FilmVip,
    onBackClick: () -> Unit
) {

    Scaffold(topBar = {
        TopBar(
            header = "Настройки поиска",
            leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            onLeftIconClick = { onBackClick() },
        )
    }, content = { innerPadding ->
        val modifier = Modifier.padding(innerPadding)

        var begin by remember {
            mutableStateOf("0")
        }
        var end by remember {
            mutableStateOf("10")
        }

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            MaterialButtonToggleGroup(
                items = listOf("Все", "Фильмы", "Сериалы"),
                selected = selectFilm(filmVip.type),
                onClick = {
                    println(" onClick Filter $it ")
                }
            )

            RowTwoText(first = "Страна", two = filmVip.countryName, onClick = { })
            RowTwoText(first = "Жанр", two = filmVip.genresName, onClick = { })
            RowTwoText(first = "Год", two = "${filmVip.yearFrom} - ${filmVip.yearTo}", onClick = { })
            RowTwoText(
                first = "Рейтинг",
                two = "$begin-$end",
                onClick = { })

            RangeSlider(
                modifier = Modifier
                    .padding(horizontal = 48.dp, vertical = 48.dp)
                    .fillMaxWidth(),
                rangeColor = Color(73, 147, 236),
                backColor = Color(203, 225, 246),
                barHeight = 8.dp,
                circleRadius = 15.dp,
                radiusRotate = 30.dp,
                radius = 15.dp,
                progress1InitialValue = 0.3f,
                progress2InitialValue = 0.8f,
                tooltipSpacing = 10.dp,
                tooltipWidth = 40.dp,
                tooltipHeight = 30.dp,
                cornerRadius = CornerRadius(32f, 32f),
                tooltipTriangleSize = 8.dp,
            ) { progress1, progress2 ->
                begin = (progress1 * 10).toString().take(3)
                end = (progress2 * 10).toString().take(3)
            }

            MaterialButtonToggleGroup(
                items = listOf("Дата", "Популярность", "Рейтинг"),
                selected = selectFilm(filmVip.order),
                onClick = {
                    println(" onClick Filter $it ")
                }
            )


        }
    })
}

fun selectFilm(type: String): Int {
    return when (type) {
        "ALL" -> 0
        "FILM" -> 1
        else -> 2
    }
}

fun selectOrder(order: String): Int {
    return when (order) {
        "Date" -> 0
        "Popular" -> 1
        else -> 2
    }
}