package com.shu.network.models_person

import com.shu.models.detail_person.MovieOfActor
import com.shu.models.detail_person.Person
import com.shu.models.details.Actor
import com.shu.network.modelDetail.ActorsDto


data class PersonDto(
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

fun PersonDto.toPerson(): Person {
    return with(this) {
        Person(
            personId = personId,
            webUrl = webUrl,
            nameRu = nameRu,
            nameEn = nameEn,
            sex = sex,
            posterUrl = posterUrl,
            growth = growth,
            birthday = birthday,
            death = death,
            age = age,
            birthplace = birthplace,
            deathplace = deathplace,
            hasAwards = hasAwards,
            profession = profession,
            facts = facts,
            spouses = spouses.map { it.toSpouses() },
            films = films.map { it.toMovieOfActor() },
        )
    }
}
