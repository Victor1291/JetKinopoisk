package com.shu.models

data class FilmVip(
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
    val title: ETitle = ETitle.Premieres,
    var countryName: String = "США",
    var genresName: String = "приключения",
    var isSearchPerson: Boolean = true,
)
