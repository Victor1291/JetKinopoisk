package com.shu.network.repository

import android.icu.text.SimpleDateFormat
import com.shu.models.ListCinema
import com.shu.models.FilmVip
import com.shu.network.ServiceMovieApi
import android.util.Log
import com.example.database.MovieDao
import com.example.database.modelDbo.FiltersDbo
import com.shu.list_movies.domain.PagingRepository
import com.shu.home.domain.HomeRepository
import com.shu.models.Choice
import com.shu.models.CinemaItem
import com.shu.models.Countries
import com.shu.models.Genres
import com.shu.models.ListFilters
import com.shu.models.ManyScreens
import com.shu.models.media_posts.ListPosts
import com.shu.network.models.filters.mapFromApi
import com.shu.network.models.filters.mapFromBd
import com.shu.network.models.filters.mapToBd
import com.shu.network.models.mapFrom
import com.shu.network.models.media_posts.toListPosts
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

class HomeRepositoryImpl @Inject constructor(
    private val api: ServiceMovieApi,
    private val movieDao: MovieDao,
) : HomeRepository, PagingRepository {
    override suspend fun getAllScreen(): ManyScreens {
        return coroutineScope {

            var listPremier: List<CinemaItem> = emptyList()
            var listPopular: List<CinemaItem> = emptyList()
            var listUSA: List<CinemaItem> = emptyList()
            var list250: List<CinemaItem> = emptyList()
            var listFrance: List<CinemaItem> = emptyList()
            var listSerials: List<CinemaItem> = emptyList()
            var genres  : List<Genres> = emptyList()

            val genreCountryBd  : FiltersDbo? = movieDao.getFilters()

            val genreCountry : ListFilters? = if (genreCountryBd?.countries.isNullOrEmpty() ) {
                val genreCountryApi = api.genreCountry()
                val gCBd = genreCountryApi.mapToBd()
                movieDao.saveToBd(gCBd)
                genreCountryApi.mapFromApi()
            } else {
                movieDao.getFilters()?.mapFromBd()
            }

            val date = Date()
            val calendar = Calendar.getInstance()
            calendar.time = date
            val year = calendar[Calendar.YEAR]
            val month = calendar.getDisplayName(
                Calendar.MONTH,
                Calendar.LONG_FORMAT, Locale("en")
            )

            launch {
                launch {
                    listPremier = getPremieres(year, month ?: "MAY").items
                }
                launch {
                    listPopular = getPopular(1).items
                }
                launch {
                    list250 = getTop250(1).items
                }
                launch {
                    if (genreCountry != null) {
                        listUSA = choiceCountry(genreCountry.countries, genreCountry.genres)
                    }
                }
                launch {
                    if (genreCountry != null) {
                        listFrance = choiceCountry(genreCountry.countries, genreCountry.genres)
                    }
                }
                launch {
                    listSerials = getSerialVip(1).items
                }
            }.join()

            return@coroutineScope ManyScreens(
                listOf(
                    listPremier,
                    listPopular,
                    list250,
                    listUSA,
                    listFrance,
                    listSerials
                )
            )
        }
    }

    override suspend fun getPosts(page: Int): ListPosts {
        return api.getPosts(page).toListPosts()
    }

    private suspend fun choiceCountry(countries: List<Countries>,genres: List<Genres>): List<CinemaItem> {

        val randomIdCountry = Random.nextInt(20)
        val randomIdGenre = Random.nextInt(13)

        val country = countries[randomIdCountry].country
        val genre = genres[randomIdGenre].genre
        val idCountry = countries[randomIdCountry].id
        val idGenres = genres[randomIdGenre].id

        val vip = Choice(
            page = 1,
            country = idCountry,
            genres = idGenres,
            countryName = country,
            genresName = genre,
            type = "FILM",
            yearFrom = 2000,
            yearTo = 2024,
            ratingFrom = 7,
            ratingTo = 10,
        )

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
        ).items.map { it.mapFrom() }
    }

    private fun getTime(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    override suspend fun getPremieres(year: Int, month: String): ListCinema {
        return api.movies(year, month).mapFrom()
    }

    override suspend fun getPopular(page: Int): ListCinema {
        return api.popular(page = page).mapFrom()
    }

    override suspend fun getTop250(page: Int): ListCinema {
        val responce = api.top250(page = page)
        Log.d("repository ", " ${responce.items.size}")
        return responce.mapFrom()
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
}
