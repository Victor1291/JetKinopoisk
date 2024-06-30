package com.shu.models

data class Choice(
    val country: Int = 1,
    val genres: Int = 1,
    val order: String = "NUM_VOTE",
    val type: String = "FILM",
    val ratingFrom: Float = 0.0f,
    val ratingTo: Float = 10.0f,
    val yearFrom: Int = 1990,
    val yearTo: Int = 2024,
    var page: Int = 1,
    val keyword: String = "",
    val idFragment: Int = 1,
    val idTitle: Int = 1,
    var countryName: String = "",
    var genresName: String = "",
)

//дата класс для перехода на фрагмент ListPage и передачи параметров
