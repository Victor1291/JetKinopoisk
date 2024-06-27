package com.example.search.domain


import com.shu.models.FilmVip
import com.shu.models.ListCinema
import com.shu.models.detail_person.ListSearchPerson
import com.shu.models.detail_person.SearchPerson

interface PagingSearchRepository {

    suspend fun getFilmVip(vip: FilmVip): ListCinema

    suspend fun searchPerson(name: String,page: Int): ListSearchPerson

    // suspend fun getGenreCountry(): ListFilters
    /*
          suspend fun getSimilarsFilm(kinopoiskId: Int): ListCinema

          suspend fun search(page: Int,query: String): ListCinema

          suspend fun getCountry(): List<Countries>

          suspend fun getGenre(): List<Genres>
          */
}