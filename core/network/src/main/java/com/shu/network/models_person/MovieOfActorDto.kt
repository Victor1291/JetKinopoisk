package com.shu.network.models_person

import com.shu.models.detail_person.MovieOfActor
import com.shu.models.detail_person.Spouses

data class MovieOfActorDto(
    val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val rating: String?,
    val general: Boolean?,
    val description: String?,
    val professionKey: String?,
)

fun MovieOfActorDto.toMovieOfActor(): MovieOfActor {
    return with(this) {
        MovieOfActor(
            filmId = filmId,
            nameRu = nameRu,
            nameEn = nameEn,
            rating = rating,
            general = general,
            description = description,
            professionKey = professionKey,
        )
    }
}
