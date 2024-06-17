package com.example.search.domain


import com.shu.models.FilmVip
import com.shu.models.ListCinema

interface PagingSearchRepository {

    suspend fun getFilmVip(vip: FilmVip): ListCinema

    // suspend fun getGenreCountry(): ListFilters
    /*
          suspend fun getSimilarsFilm(kinopoiskId: Int): ListCinema

          suspend fun search(page: Int,query: String): ListCinema

          suspend fun getCountry(): List<Countries>

          suspend fun getGenre(): List<Genres>
          */
}