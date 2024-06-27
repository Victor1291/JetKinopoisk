package com.example.filter

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.cinema_elements.TopBar
import com.example.filter.components.MaterialButtonToggleGroup
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

        MaterialButtonToggleGroup(
            modifier = modifier,
            items = listOf("Все", "Фильмы", "Сериалы"),
            selected =selectFilm(filmVip.type),
            onClick = {
                println(" onClick Filter $it ")
            }
        )


    })
}

fun selectFilm(type: String): Int {
    return when (type) {
        "ALL" -> 0
        "FILM" -> 1
        else -> 2
    }
}