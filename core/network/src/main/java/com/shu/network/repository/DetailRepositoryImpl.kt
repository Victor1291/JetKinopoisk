package com.shu.network.repository

import com.example.database.MovieDao
import com.example.database.modelDbo.InterestingMovieDbo
import com.example.database.modelDbo.MovieCountriesJoin
import com.shu.models.CinemaItem
import com.shu.models.ManyScreens
import com.shu.models.details.Actor
import com.shu.models.details.DetailMovie
import com.shu.models.details.DetailUi
import com.shu.models.gallery_models.ListGalleryItems
import com.shu.models.similar_models.ListSimilar
import com.shu.network.ServiceMovieApi
import com.shu.network.modelDetail.mapFromApi
import com.shu.network.modelDetail.mapToBd
import com.shu.network.modelDetail.toActor
import com.shu.network.models.filters.mapToBd
import com.shu.network.models.gallery_models.toListGalleryItems
import com.shu.network.models.similar_models.toListSimilar
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val api: ServiceMovieApi,
    private val movieDao: MovieDao,
) : com.shu.detail_movie.domain.DetailRepository {
    override suspend fun getDetailUi(filmId: Int, type: String, page: Int): DetailUi {
        return coroutineScope {

            var film: DetailMovie = DetailMovie()
            var actorFilm: List<Actor> = emptyList()
            var gallery: ListGalleryItems = ListGalleryItems()
            var similarsFilm: ListSimilar = ListSimilar()
            launch {
                launch {
                    film =getFilm(filmId)
                }
                launch {
                    actorFilm = getActorFilm(filmId)
                }
                launch {
                    gallery = getGallery(filmId,type,page)
                }
                launch {
                    similarsFilm = getSimilarsFilm(filmId)
                }
            }.join()

            return@coroutineScope DetailUi(
                film = film,
                actorFilm = actorFilm,
                gallery = gallery,
                similarsFilm = similarsFilm
            )
        }
    }

        override suspend fun getFilm(kinopoiskId: Int): DetailMovie {

            movieDao.addInterestingMovie(InterestingMovieDbo(kinopoiskId = kinopoiskId))

            val detailMovie = api.getFilm(kinopoiskId)
            movieDao.save(detailMovie.mapToBd())
            val listJoins = mutableListOf<MovieCountriesJoin>()
            detailMovie.countries.forEach { country ->
                listJoins.add(
                    MovieCountriesJoin(
                        kinopoiskId = detailMovie.kinopoiskId,
                        country = country.country
                    )
                )
            }
            val arrayJoins = listJoins.toList().toTypedArray()
            movieDao.save(*arrayJoins)

            return detailMovie.mapFromApi()
        }

        override suspend fun getActorFilm(kinopoiskId: Int): List<Actor> {
            return api.actors(kinopoiskId).map { it.toActor() }
        }

        override suspend fun getSimilarsFilm(kinopoiskId: Int): ListSimilar {
            return api.similar(kinopoiskId).toListSimilar()
        }

        override suspend fun getGallery(
            kinopoiskId: Int,
            type: String,
            page: Int
        ): ListGalleryItems {
            return api.galleryTotal(kinopoiskId).toListGalleryItems()
        }

        /* override suspend fun getActorFilm(kinopoiskId: Int): List<ActorItem> {
             return api.actors(kinopoiskId)
         }

         override suspend fun getSimilarsFilm(kinopoiskId: Int): ListCinema {

             val listSimilar = api.similar(kinopoiskId)

             *//*listSimilar.items = listSimilar.items.map{

            //связываем id с id похожих
            movieDao.addSimilar(
                SimilarMovieDbo(
                    kinopoiskId, it.kinopoiskId
                )
            )

            //проверяем наличие в базе данных
            val film = movieDao.getMovie(it.kinopoiskId)

            if (film != null) {
                it.favorite = film.favorite
                it.watched = film.watched
                it.seeLater = film.seeLater
                it
            } else {
                //если нет то сохраняем в бд
                movieToBd(it)

                it
            }
        }*//*
        return listSimilar
    }

    override suspend fun getGallery(
        kinopoiskId: Int,
        type: String,
        page: Int
    ): GalleryHorizontalItem {
        val list = api.gallery(
            id = kinopoiskId,
            type = type,
            page = page
        )
        return GalleryHorizontalItem(
            filmId = kinopoiskId,
            title = "Галлерея",
            total = list.total,
            totalPages = list.totalPages,
            items = list.items.map {
                Galleryold(
                    imageUrl = it.imageUrl,
                    previewUrl = it.previewUrl
                )
            }
        )
    }

    override suspend fun getSerial(kinopoiskId: Int): SeasonsSerial {
        return api.serial(kinopoiskId)
    }

    override suspend fun heart(id: Int?, select: Boolean) {
        // делаем метку фильма "Любимые"
        movieDao.updateFavorite(id, select)
        //сохраняем в коллекцию в бд
        addOrDelMovie(id, select, 1)
    }

    private suspend fun addOrDelMovie(id: Int?, select: Boolean, collectionId: Int) {
        if (select) {
            // добавить фильм в коллекцию.
            if (id != null) {
                movieDao.addMovie(
                    CollectionsMovieDbo(
                        collectionId = collectionId,
                        kinopoiskId = id
                    )
                )
                //увеличиваем счётчик
                movieDao.updateCollection(collectionId)
            }
        } else {
            // удалить фильм из коллекции.
            if (id != null) {
                movieDao.addMovieDel(collectionId, id)
                //уменьшить счётчик
                movieDao.updateCollectionDel(collectionId)
            }
        }
    }

    override suspend fun seeLater(id: Int?, select: Boolean) {
        //сохраняем в коллекцию в бд и делаем метку фильма "Хочу посмотреть"
        movieDao.updateSeeLater(id, select)
        addOrDelMovie(id,select,2)
    }

    override suspend fun watched(id: Int?, select: Boolean) {
        movieDao.updateWatched(id, select)
    }

    private suspend fun movieToBd(movie: CinemaItem) {
        movieDao.insert(
            MovieDbo(
                kinopoiskId = movie.kinopoiskId,
                nameRu = movie.nameRu,
                nameEn = movie.nameEn,
                imdbId = movie.imdbId,
                posterUrlPreview = movie.posterUrlPreview,
                ratingNew = movie.ratingNew,
                countriesList = movie.countriesList,
                filmLength = movie.filmLength,
                genresList = movie.genresList,
                posterUrl = movie.posterUrl,
                year = movie.year,
                coverUrl = movie.coverUrl,
                description = movie.description,
                logoUrl = movie.logoUrl,
                ratingAgeLimits = movie.ratingAgeLimits,
                shortDescription = movie.shortDescription,
                serial = movie.serial
            )
        )

    }*/
    }

