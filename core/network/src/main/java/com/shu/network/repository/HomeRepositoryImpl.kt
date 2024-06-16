package com.shu.network.repository

import android.icu.text.SimpleDateFormat
import com.shu.models.ListCinema
import com.shu.models.FilmVip
import com.shu.network.ServiceMovieApi
import com.shu.network.models.filters.ListFiltersDto
import android.util.Log
import com.shu.list_movies.domain.PagingRepository
import com.shu.home.domain.HomeRepository
import com.shu.models.Choice
import com.shu.models.CinemaItem
import com.shu.models.ManyScreens
import com.shu.models.media_posts.ListPosts
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
) : HomeRepository, PagingRepository {
    override suspend fun getAllScreen(): ManyScreens {
        return coroutineScope {

            var listPremier: List<CinemaItem> = emptyList()
            var listPopular: List<CinemaItem> = emptyList()
            var listUSA: List<CinemaItem> = emptyList()
            var list250: List<CinemaItem> = emptyList()
            var listFrance: List<CinemaItem> = emptyList()
            var listSerials: List<CinemaItem> = emptyList()

            val genreCountry = api.genreCountry()
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
                    listUSA = choiceCountry(genreCountry)
                }
                launch {
                    listFrance = choiceCountry(genreCountry)
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

    private suspend fun choiceCountry(lists: ListFiltersDto): List<CinemaItem> {

        val randomIdCountry = Random.nextInt(20)
        val randomIdGenre = Random.nextInt(13)

        val country = lists.countries[randomIdCountry].country
        val genre = lists.genres[randomIdGenre].genre
        val idCountry = lists.countries[randomIdCountry].id
        val idGenres = lists.genres[randomIdGenre].id

        val vip = Choice(
            page = 1,
            country = idCountry,
            genres = idGenres,
            countryName = country,
            genresName = genre,
            type = "FILM"
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
