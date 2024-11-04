package com.shu.list_movies.domain


import androidx.paging.PagingData
import com.shu.models.CinemaItem
import com.shu.models.ETitle
import com.shu.models.FilmVip
import com.shu.models.ListCinema
import kotlinx.coroutines.flow.Flow

interface PagingRepository {

    suspend fun getPremieres(year: Int, month: String): ListCinema

    suspend fun getPopular(page: Int): ListCinema

    suspend fun getTop250(page: Int): ListCinema

    suspend fun getFilmVip(vip: FilmVip): ListCinema
    suspend fun getSerialVip(page: Int): ListCinema

    fun getOrderingCash(vip: FilmVip): Flow<PagingData<CinemaItem>>
   // fun getOrdering(vip: FilmVip): Flow<PagingData<CinemaItem>>

    // suspend fun getGenreCountry(): ListFilter    s
/*
      suspend fun getSimilarsFilm(kinopoiskId: Int): ListCinema

      suspend fun search(page: Int,query: String): ListCinema

      suspend fun getCountry(): List<Countries>

      suspend fun getGenre(): List<Genres>
      */
}