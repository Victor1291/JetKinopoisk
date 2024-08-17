package com.example.filter

import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.design_system.component.Excell
import com.example.design_system.component.StartCalendar
import com.shu.models.FilmVip

@Composable
fun CalendarView(
    viewModel: FilterViewModel,
    onDismiss: () -> Unit
) {

    val filter = viewModel.filter.collectAsState()
    //Первая таблица
    val start = remember { mutableIntStateOf(2005) }
    val end = remember { mutableIntStateOf(2024) }
    val select = remember { mutableIntStateOf(4) }
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
    val select2 = remember { mutableIntStateOf(47) }
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
            select.intValue = it
        },
        onSelect2 = {
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
            viewModel.setFilter(
                filter.value.copy(
                    yearFrom = list[select.intValue].toInt(),
                    yearTo = list[select2.intValue].toInt()
                )
            )
            onDismiss()
        }
    )
}
