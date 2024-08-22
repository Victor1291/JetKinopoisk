package com.example.filter

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
    onSelectYear: (FilmVip) -> Unit,
) {
    val type = listOf("ALL", "FILM", "TV_SERIES")
    val order = listOf("YEAR", "NUM_VOTE", "RATING")
    LaunchedEffect(key1 = true) {
        Log.i("searchFilter", "start $filmVip ")
        viewModel.setFilter(filmVip)

    }

    val filter = viewModel.filter.collectAsState()

    val openDialog = remember { mutableStateOf(false) }
    val openDialogCity = remember { mutableStateOf(false) }
    val openDialogGenre = remember { mutableStateOf(false) }
    if (openDialog.value) {
        Dialog(
            onDismissRequest = { openDialog.value = false },
            properties = DialogProperties()
        ) {

            Card(
                shape = RoundedCornerShape(10.dp),
                modifier= Modifier.height(450.dp).width(200.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                CalendarView(viewModel = viewModel,
                    onDismiss = {
                        openDialog.value = false
                    }
                )
            }
        }
    }
    if (openDialogCity.value) {
        Dialog(
            onDismissRequest = { openDialogCity.value = false },
            properties = DialogProperties()
        ) {
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier= Modifier.height(450.dp).width(200.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                CountryDialogView(viewModel = viewModel,
                    onDismiss = {
                        openDialogCity.value = false
                    }
                )
            }
        }

    }
    if (openDialogGenre.value) {
        Dialog(
            onDismissRequest = { openDialogGenre.value = false },
            properties = DialogProperties()
        ) {

            Card(
                shape = RoundedCornerShape(10.dp),
                modifier= Modifier.height(450.dp).width(200.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                GenreDialogView(viewModel = viewModel,
                    onDismiss = {
                        openDialogGenre.value = false
                    }
                )
            }
        }

    }

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
            selected = type.indexOf(filter.value.type),
            onClick = { index ->
                viewModel.setFilter(filter.value.copy(type = type[index]))
            }
        )

        RowTwoText(first = "Страна", second = filter.value.countryName, onClick = { openDialogCity.value = true })
        RowTwoText(first = "Жанр", second = filter.value.genresName, onClick = { openDialogGenre.value = true })
        RowTwoText(
            first = "Год",
            second = "${filter.value.yearFrom} - ${filter.value.yearTo}",
            onClick = { openDialog.value = true })
        RowTwoText(
            first = "Рейтинг",
            second = "${filter.value.ratingFrom} - ${filter.value.ratingTo}",
            onClick = { })
        /* RowTwoText(
             first = "Рейтинг2",
             second = "${filter.value.ratingFrom.div(10)} - ${filter.value.ratingTo.div(10)}",
             onClick = { })*/

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
            selected = order.indexOf(filter.value.order),
            onClick = { index ->
                viewModel.setFilter(filter.value.copy(order = order[index]))
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

