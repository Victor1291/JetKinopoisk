package com.shu.models

data class CinemaItem(

    val kinopoiskId:     Int?,
    val nameRu:          String?,
    val nameEn:          String?,
    val nameOriginal:    String?,
    val posterUrl:       String?,
    val posterUrlPreview: String?,
    var year:               Int?,
    var rating:             String?,
    val countries:           List<Countries>,
    val genres:             List<Genres>,
    val premiereRu:          String?,

)