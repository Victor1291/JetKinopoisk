package com.shu.network.repository

import android.icu.text.SimpleDateFormat
import android.util.Log
import com.example.database.MovieDao
import com.example.database.modelDbo.FiltersDbo
import com.shu.home.domain.HomeRepository
import com.shu.list_movies.domain.PagingRepository
import com.shu.models.CinemaItem
import com.shu.models.Countries
import com.shu.models.FilmVip
import com.shu.models.Genres
import com.shu.models.ListCinema
import com.shu.models.ListFilters
import com.shu.models.ManyScreens
import com.shu.models.media_posts.ListPosts
import com.shu.network.ServiceMovieApi
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
            val listTitle = mutableListOf("Премьеры","Популярное","Топ 250","","","Сериалы")

            //Получаю списки Стран и жанров из базы данных
            val genreCountryBd: FiltersDbo? = movieDao.getFilters()

            //Проверка, и получение списка из Апи.
            val genreCountry: ListFilters? = if (genreCountryBd?.countries.isNullOrEmpty()) {
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
           var filmVipOne = FilmVip()
           var filmVipTwo = FilmVip()

            launch {

                launch {
                    listPremier = getPremieres(year, month ?: "MAY").items.shuffled()
                }
                launch {
                    listPopular = getPopular(1).items
                }
                launch {
                    list250 = getTop250(1).items

                }
                launch {
                    if (genreCountry != null) {
                        filmVipOne = choiceCountry(genreCountry.countries, genreCountry.genres)
                        listUSA = api.filmVip(
                            page = filmVipOne.page,
                            country = filmVipOne.country,
                            genres = filmVipOne.genres,
                        ).items.map { it.mapFrom() }
                        listTitle[3] = ("${genreCountry.genres[filmVipOne.country].genre} ${genreCountry.countries[filmVipOne.country].country} ")
                    }
                }
                launch {
                    if (genreCountry != null) {
                        filmVipTwo = choiceCountry(genreCountry.countries, genreCountry.genres)
                        listFrance = api.filmVip(
                            page = filmVipTwo.page,
                            country = filmVipTwo.country,
                            genres = filmVipTwo.genres,
                        ).items.map { it.mapFrom() }
                        listTitle[4] = ("${genreCountry.genres[filmVipTwo.country].genre} ${genreCountry.countries[filmVipTwo.country].country} ")
                    }
                }
                launch {
                    listSerials = getSerialVip(1).items
                }
            }.join()

            return@coroutineScope ManyScreens(
                homeListScreen = listOf(
                    listPremier,
                    listPopular,
                    list250,
                    listUSA,
                    listFrance,
                    listSerials
                ),
                listTitle = listTitle.toList(),
                filmVipOne = filmVipOne,
                filmVipTwo = filmVipTwo,
            )
        }
    }

    override suspend fun getPosts(page: Int): ListPosts {
        return api.getPosts(page).toListPosts()
    }

    private fun choiceCountry(countries: List<Countries>, genres: List<Genres>): FilmVip {

        val randomIdCountry = Random.nextInt(20)
        val randomIdGenre = Random.nextInt(13)

       // val country = countries[randomIdCountry].country
//val genre = genres[randomIdGenre].genre
        val idCountry = countries[randomIdCountry].id
        val idGenres = genres[randomIdGenre].id


        return FilmVip(
            page = 1,
            country = idCountry,
            genres = idGenres,
            order = "",
            type = "FILM",
            ratingFrom = 5.0f,
            ratingTo = 10.0f,
            yearFrom = 2000,
            yearTo = 2024,
            keyword = ""
        )
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
