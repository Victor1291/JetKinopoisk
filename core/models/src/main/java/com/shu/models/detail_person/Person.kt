package com.shu.models.detail_person



data class Person (
    val personId: Int?,
    val webUrl: String?,
    val nameRu: String?,
    val nameEn: String?,
    val sex: String?,
    val posterUrl: String?,
    val growth: String?,
    val birthday: String?,
    val death: String?,
    val age: Int?,
    val birthplace: String?,
    val deathplace: String?,
    val hasAwards: Int?,
    val profession: String?,
    val facts: List<String>,
    val spouses: List<Spouses>,
    val films: List<MovieOfActor>,
)

