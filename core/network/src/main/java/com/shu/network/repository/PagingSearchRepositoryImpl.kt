package com.shu.network.repository

import android.util.Log
import com.example.search.domain.PagingSearchRepository
import com.shu.models.FilmVip
import com.shu.models.ListCinema
import com.shu.models.detail_person.ListSearchPerson

import com.shu.network.ServiceMovieApi
import com.shu.network.models.mapFrom
import com.shu.network.models_person.toListSearchPerson
import javax.inject.Inject

class PagingSearchRepositoryImpl @Inject constructor(
    private val api: ServiceMovieApi,
) : PagingSearchRepository {


    override suspend fun getFilmVip(vip: FilmVip): ListCinema {
        Log.d("search getFilmVip", "order =  ${vip.order} type = ${vip.type}")
        return api.filmVip(
            page = vip.page,
            country = vip.country,
            genres = vip.genres,
            order = vip.order,
            type = vip.type,
            ratingFrom = vip.ratingFrom,
            ratingTo = vip.ratingTo,
            yearFrom = vip.yearFrom,
            yearTo = vip.yearTo,
            keyword = vip.keyword
        ).mapFrom()
    }

    override suspend fun searchPerson(name: String, page: Int): ListSearchPerson {
        return api.searchPerson(name,page).toListSearchPerson()
    }

}
