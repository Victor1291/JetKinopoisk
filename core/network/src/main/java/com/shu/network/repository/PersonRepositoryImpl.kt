package com.shu.network.repository

import com.shu.detail_person.domain.PersonRepository
import com.shu.models.detail_person.Person
import com.shu.models.details.DetailMovie
import com.shu.network.ServiceMovieApi
import com.shu.network.modelDetail.mapFrom
import com.shu.network.models_person.toPerson
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val api: ServiceMovieApi,
) : PersonRepository {

    override suspend fun getPerson(personId: Int): Person {
        return api.person(personId).toPerson()

    }

    override suspend fun getFilm(kinopoiskId: Int): DetailMovie {
        return api.getFilm(kinopoiskId).mapFrom()
    }
}

