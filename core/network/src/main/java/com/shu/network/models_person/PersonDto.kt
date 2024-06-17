package com.shu.network.models_person



data class PersonDto (
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
    val spouses: List<SpousesDto>,
    val films: List<MovieOfActorDto>,
)

