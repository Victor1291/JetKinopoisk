package com.shu.models

data class FilmVip(
    val country: Int = 1,
    val genres: Int = 1,
    val order: String = "NUM_VOTE",
    val type: String = "FILM",
    val ratingFrom: Int = 0,
    val ratingTo: Int = 10,
    val yearFrom: Int = 1990,
    val yearTo: Int = 2024,
    var page: Int = 1,
    val keyword: String = "",
    val title: ETitle = ETitle.Premieres,
    var countryName: String = "",
    var genresName: String = "",
    var isSearchPerson: Boolean = true,
)
