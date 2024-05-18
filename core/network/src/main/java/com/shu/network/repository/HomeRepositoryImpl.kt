package com.shu.network.repository

import com.shu.models.ListCinema
import com.shu.models.FilmVip
import com.shu.network.ServiceMovieApi
import android.util.Log
import com.shu.network.models.mapFrom
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: ServiceMovieApi,
) : HomeRepository {

    override suspend fun getPremieres(year: Int, month: String): ListCinema {
        return api.movies(year, month).mapFrom()
    }

    override suspend fun getPopular(page: Int): ListCinema {
        return api.popular(page = page).mapFrom()
    }

    override suspend fun getTop250(page: Int): ListCinema {
        return api.top250(page = page).mapFrom()
    }

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

    override suspend fun getSerialVip(page: Int): ListCinema {
        return api.serialVip(page = page).mapFrom()
    }

    /* override suspend fun getGenreCountry(): ListFilters {

         val genres = movieDao.getGenres()
         val countries = movieDao.getCountries()
         var list: ListFiltersDto
         if (genres.isNotEmpty() && countries.isNotEmpty()) {
             list = ListFiltersDto(
                 genres = genres.map {
                     GenresDto(
                         id = it.id,
                         genre = it.genre
                     )
                 },
                 countries = countries.map {
                     CountriesDto(
                         id = it.id,
                         country = it.country
                     )
                 }
             )
         } else {
             list = api.genreCountry()
             movieDao.addGenres(list.genres.map {
                 GenresDbo(
                     id = it.id ?: 0,
                     genre = it.genre
                 )
             })
             movieDao.addCountry(list.countries.map {
                 CountriesDbo(
                     id = it.id ?: 0,
                     country = it.country
                 )
             })

         }
         return list
     }*/

    /*  override suspend fun getSimilarsFilm(kinopoiskId: Int): ListCinema {
          //не используется
          return api.similar(kinopoiskId)
      }

      override suspend fun search(page: Int, query: String): ListCinema {
          return api.search(page = page, keyword = query)
      }*/

    /*override suspend fun getCountry(): List<Countries> {
        return movieDao.getCountries()
    }

    override suspend fun getGenre(): List<Genres> {
        return movieDao.getGenres()
    }
*/
    /*  override suspend fun getWatched(): List<CinemaItem> {
          return movieDao.getWatchedMovies()
      }*/
}
