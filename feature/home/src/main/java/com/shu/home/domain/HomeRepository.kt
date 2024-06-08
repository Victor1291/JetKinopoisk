package com.shu.home.domain


import com.shu.models.ListCinema
import com.shu.models.ManyScreens
import com.shu.models.FilmVip

interface HomeRepository {

    suspend fun getAllScreen() : ManyScreens
    suspend fun getPremieres(year: Int, month: String): ListCinema

    suspend fun getPopular(page: Int): ListCinema

    suspend fun getTop250(page: Int): ListCinema

    suspend fun getFilmVip(vip: FilmVip): ListCinema
    suspend fun getSerialVip(page: Int): ListCinema

     // suspend fun getGenreCountry(): ListFilters
/*
      suspend fun getSimilarsFilm(kinopoiskId: Int): ListCinema

      suspend fun search(page: Int,query: String): ListCinema

      suspend fun getCountry(): List<Countries>

      suspend fun getGenre(): List<Genres>
      */
}