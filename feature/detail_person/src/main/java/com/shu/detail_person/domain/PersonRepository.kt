package com.shu.detail_person.domain

import com.shu.models.CinemaItem
import com.shu.models.ListCinema
import com.shu.models.detail_person.Person
import com.shu.models.details.DetailMovie


interface PersonRepository {

    suspend fun getDPerson(personId: Int): Person

    suspend fun getFilm(kinopoiskId: Int): DetailMovie

}