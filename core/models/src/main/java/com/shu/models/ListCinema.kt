package com.shu.models

data class ListCinema(
    val total: Int? = null,
    val totalPages : Int? = null,
    val items: List<CinemaItem> = listOf()
)