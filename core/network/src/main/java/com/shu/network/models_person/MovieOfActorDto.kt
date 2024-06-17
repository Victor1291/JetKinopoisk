package com.shu.network.models_person

data class MovieOfActorDto (
    val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val rating: String?,
    val general: Boolean?,
    val description: String?,
    val professionKey: String?,
)
