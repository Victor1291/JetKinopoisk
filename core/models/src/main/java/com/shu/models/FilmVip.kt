package com.shu.models

data class FilmVip(
    val country : Int,
    val genres : Int,
    val order: String,
    val type: String,
    val ratingFrom: Int,
    val ratingTo: Int,
    val yearFrom: Int,
    val yearTo: Int,
    val page: Int,
    val keyword: String,
    val idFragment : Int,
    val idTitle: Int,
    val countryName: String,
    val genresName: String
)
