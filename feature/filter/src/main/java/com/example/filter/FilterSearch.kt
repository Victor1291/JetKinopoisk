package com.example.filter

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.design_system.component.MaterialButtonToggleGroup
import com.example.design_system.component.NiaFilterChip
import com.example.design_system.component.RangeSlider
import com.example.design_system.component.RowTwoText
import com.example.design_system.component.TopBar
import com.shu.models.FilmVip
import kotlin.math.roundToInt

@Composable
fun FilterSearch(
    modifier: Modifier,
    filmVip: FilmVip,
    viewModel: FilterViewModel = hiltViewModel(),
    onBackClick: (FilmVip) -> Unit,
    onCountryClick: (FilmVip) -> Unit,
    onGenresClick: (FilmVip) -> Unit,
) {

    LaunchedEffect(key1 = true) {
        Log.i("searchFilter", "start $filmVip ")
        viewModel.setFilter(filmVip)
    }

    val filter = viewModel.filter.collectAsState()


    var select = remember {
        mutableStateOf(true)
    }
    Column(
        modifier = modifier
    ) {

        TopBar(
            header = "Настройки поиска",
            leftIconImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            onLeftIconClick = { onBackClick(filter.value) },
        )
        MaterialButtonToggleGroup(
            items = listOf("Все", "Фильмы", "Сериалы"),
            selected = selectFilm(filmVip.type),
            onClick = {
                onCountryClick(filter.value)
            }
        )

        RowTwoText(first = "Страна", second = filter.value.countryName, onClick = { })
        RowTwoText(first = "Жанр", second = filter.value.genresName, onClick = { })
        RowTwoText(
            first = "Год",
            second = "${filter.value.yearFrom} - ${filter.value.yearTo}",
            onClick = { })
        RowTwoText(
            first = "Рейтинг",
            second = "${filter.value.ratingFrom} - ${filter.value.ratingTo}",
            onClick = { })
        RowTwoText(
            first = "Рейтинг2",
            second = "${filter.value.ratingFrom.div(10)} - ${filter.value.ratingTo.div(10)}",
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
            progress1InitialValue = filter.value.ratingFrom.div(10),
            progress2InitialValue = filter.value.ratingTo.div(10),
            tooltipSpacing = 10.dp,
            tooltipWidth = 40.dp,
            tooltipHeight = 30.dp,
            cornerRadius = CornerRadius(32f, 32f),
            tooltipTriangleSize = 8.dp,
        ) { progress1, progress2 ->
            val begin = (progress1 * 100).roundToInt().toFloat().div(10)
            val end = (progress2 * 100).roundToInt().toFloat().div(10)
            viewModel.setFilter(filter.value.copy(ratingFrom = begin, ratingTo = end))
        }

        MaterialButtonToggleGroup(
            items = listOf("Дата", "Популярность", "Рейтинг"),
            selected = selectOrder(filmVip.order),
            onClick = {
                onGenresClick(filter.value)
            }
        )

        NiaFilterChip(
            selected = select.value,
            iconFirst = Icons.Rounded.Check,
            onSelectedChange = { select.value = !select.value }) {
            Text(text = if (select.value) "просмотреные" else "не просмотренные")
        }

    }
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