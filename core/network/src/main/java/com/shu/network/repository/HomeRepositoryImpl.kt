package com.shu.network.repository

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.database.MovieDao
import com.example.database.MovieDatabase
import com.example.database.modelDbo.FiltersDbo
import com.shu.home.domain.HomeRepository
import com.shu.list_movies.domain.PagingRepository
import com.shu.models.CinemaItem
import com.shu.models.FilmVip
import com.shu.models.ListCinema
import com.shu.models.ListFilters
import com.shu.models.ManyScreens
import com.shu.models.media_posts.ListPosts
import com.shu.network.ServiceMovieApi
import com.shu.network.models.filters.mapFromApi
import com.shu.network.models.filters.mapFromBd
import com.shu.network.models.filters.mapToBd
import com.shu.network.models.mapFrom
import com.shu.network.models.mapFromBd
import com.shu.network.models.media_posts.toListPosts
import com.shu.network.paging.MovieRemoteMediator
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

/**
 * Уже писал про репозитории в app модуле, повторюсь, кажется их нужно раскидать по фичам, либо сделать
 * базовые модули
 */
class HomeRepositoryImpl @Inject constructor(
    private val api: ServiceMovieApi,
    private val movieDao: MovieDao,
    private val database: MovieDatabase,
) : HomeRepository, PagingRepository {

    override suspend fun getAllNewScreen(): ManyScreens {

        return coroutineScope {

            val listTitle = mutableListOf("Премьеры", "Популярное", "Топ 250", "", "", "Сериалы")
            val genreCountryBd: FiltersDbo? = movieDao.getFilters()
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

            val listPremier = async { getPremieres(year, month ?: "MAY").items.shuffled() }
            val listPopular = async { getPopular(1).items }
            val list250 = async { getTop250(1).items }
            val listUSA = async {
                filmVipOne = choiceCountry()
                if (genreCountry != null) {
                    listTitle[3] =
                        ("${genreCountry.countries[filmVipOne.country - 1].country} ${genreCountry.genres[filmVipOne.genres - 1].genre} ")
                }
                api.filmVip(
                    page = filmVipOne.page,
                    country = filmVipOne.country,
                    genres = filmVipOne.genres,
                ).items.map { it.mapFrom() }
            }
            val listFrance = async {
                filmVipTwo = choiceCountry()
                if (genreCountry != null) {
                    listTitle[4] =
                        ("${genreCountry.countries[filmVipTwo.country - 1].country} ${genreCountry.genres[filmVipTwo.genres - 1].genre} ")
                }
                api.filmVip(
                    page = filmVipTwo.page,
                    country = filmVipTwo.country,
                    genres = filmVipTwo.genres,
                ).items.map { it.mapFrom() }
            }
            val listSerials = async { getSerialVip(1).items }

            return@coroutineScope ManyScreens(
                homeListScreen = listOf(
                    listPremier.await(),
                    listPopular.await(),
                    list250.await(),
                    listUSA.await(),
                    listFrance.await(),
                    listSerials.await()
                ),
                listTitle = listTitle.toList(),
                filmVipOne = filmVipOne,
                filmVipTwo = filmVipTwo,
            )
        }
    }

    override suspend fun getAllScreen(): ManyScreens {
        return coroutineScope {

            var listPremier: List<CinemaItem> = emptyList()
            var listPopular: List<CinemaItem> = emptyList()
            var listUSA: List<CinemaItem> = emptyList()
            var list250: List<CinemaItem> = emptyList()
            var listFrance: List<CinemaItem> = emptyList()
            var listSerials: List<CinemaItem> = emptyList()
            val listTitle = mutableListOf("Премьеры", "Популярное", "Топ 250", "", "", "Сериалы")

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
                        filmVipOne = choiceCountry()
                        listUSA = api.filmVip(
                            page = filmVipOne.page,
                            country = filmVipOne.country,
                            genres = filmVipOne.genres,
                        ).items.map { it.mapFrom() }
                        listTitle[3] =
                            ("${genreCountry.countries[filmVipOne.country - 1].country} ${genreCountry.genres[filmVipOne.genres - 1].genre} ")
                    }
                }
                launch {
                    if (genreCountry != null) {
                        filmVipTwo = choiceCountry()
                        listFrance = api.filmVip(
                            page = filmVipTwo.page,
                            country = filmVipTwo.country,
                            genres = filmVipTwo.genres,
                        ).items.map { it.mapFrom() }
                        listTitle[4] =
                            ("${genreCountry.countries[filmVipTwo.country - 1].country} ${genreCountry.genres[filmVipTwo.genres - 1].genre} ")
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

    //TODO сделать отдельный медиатор для постов.
    override suspend fun getPosts(page: Int): ListPosts {
        return api.getPosts(page).toListPosts()
    }

    private fun choiceCountry(): FilmVip {

        val randomIdCountry = Random.nextInt(20) + 1
        val randomIdGenre = Random.nextInt(13) + 1
        return FilmVip(
            page = 1,
            country = randomIdCountry,
            genres = randomIdGenre,
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

    @OptIn(ExperimentalPagingApi::class)
    override fun getOrderingCash(
        vip: FilmVip,
        isSkipRefresh: Boolean
    ): Flow<PagingData<CinemaItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10, initialLoadSize = 15, prefetchDistance = 4),
            pagingSourceFactory = { database.getMovieMediatorDao().getMovies() },
            remoteMediator = MovieRemoteMediator(
                api = api,
                dataBase = database,
                vip = vip,
                isSkipRefresh = isSkipRefresh
            )
        ).flow.map { pagingData ->
            pagingData.map { it.mapFromBd() }
        }
    }


    /*   override fun getOrdering(vip: FilmVip): Flow<PagingData<CinemaItem>> {
           return Pager(
               config = PagingConfig(pageSize = 10, initialLoadSize = 15, prefetchDistance = 4),
               pagingSourceFactory = { MoviePagingSource(api, vip) },
           ).flow
       }*/
}
