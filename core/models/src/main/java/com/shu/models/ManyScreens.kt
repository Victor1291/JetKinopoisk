package com.shu.models

data class ManyScreens(
    val homeListScreen: List<List<CinemaItem>>,
    val listTitle: List<String>,
    val filmVipOne: FilmVip,
    val filmVipTwo: FilmVip,
)
