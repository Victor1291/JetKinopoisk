package com.shu.network.models_person

import com.google.gson.annotations.SerializedName
import com.shu.models.detail_person.Person
import com.shu.models.detail_person.SearchPerson

data class PersonDto(
    @SerializedName(value = "personId", alternate = ["kinopoiskId"])
    val personId: Int?,
    @SerializedName("webUrl")
    val webUrl: String?,
    @SerializedName("nameRu")
    val nameRu: String?,
    @SerializedName("nameEn")
    val nameEn: String?,
    @SerializedName("sex")
    val sex: String?,
    @SerializedName("posterUrl")
    val posterUrl: String?,
    @SerializedName("growth")
    val growth: String?,
    @SerializedName("birthday")
    val birthday: String?,
    @SerializedName("death")
    val death: String?,
    @SerializedName("age")
    val age: Int?,
    @SerializedName("birthplace")
    val birthplace: String?,
    @SerializedName("deathplace")
    val deathplace: String?,
    @SerializedName("hasAwards")
    val hasAwards: Int?,
    @SerializedName("profession")
    val profession: String?,
    @SerializedName("facts")
    val facts: List<String>,
    @SerializedName("spouses")
    val spouses: List<SpousesDto>,
    @SerializedName("films")
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

fun PersonDto.toSearchPerson(): SearchPerson {
    return with(this) {
        SearchPerson(
            personId = personId,
            webUrl = webUrl,
            nameRu = nameRu,
            nameEn = nameEn,
            sex = sex,
            posterUrl = posterUrl,
        )
    }
}


