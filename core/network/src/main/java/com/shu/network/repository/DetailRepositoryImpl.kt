package com.shu.network.repository

import com.example.database.MovieDao
import com.example.database.modelDbo.CollectionsMovieDbo
import com.example.database.modelDbo.InterestingMovieDbo
import com.example.database.modelDbo.MovieCountriesJoin
import com.shu.models.details.Actor
import com.shu.models.details.DetailMovie
import com.shu.models.details.DetailUi
import com.shu.models.gallery_models.ListGalleryItems
import com.shu.models.similar_models.ListSimilar
import com.shu.network.ServiceMovieApi
import com.shu.network.modelDetail.mapFromApi
import com.shu.network.modelDetail.mapToBd
import com.shu.network.modelDetail.toActor
import com.shu.network.models.gallery_models.toListGalleryItems
import com.shu.network.models.similar_models.toListSimilar
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val api: ServiceMovieApi,
    private val movieDao: MovieDao,
) : com.shu.detail_movie.domain.DetailRepository {
    override suspend fun getDetailUi(kinopoiskId: Int, type: String, page: Int): DetailUi {

        return coroutineScope {

            val film = async { getFilm(kinopoiskId) }
            val actorFilm = async { getActorFilm(kinopoiskId) }
            val gallery = async { getGallery(kinopoiskId, type, page) }
            val similarsFilm = async { getSimilarsFilm(kinopoiskId) }

            return@coroutineScope DetailUi(
                film = film.await(),
                actorFilm = actorFilm.await(),
                gallery = gallery.await(),
                similarsFilm = similarsFilm.await()
            )
        }
    }

    override suspend fun getFilm(kinopoiskId: Int): DetailMovie {

        movieDao.addInterestingMovie(InterestingMovieDbo(kinopoiskId = kinopoiskId))
        val filmInBd = movieDao.getMovie(kinopoiskId)

        val detailMovie = api.getFilm(kinopoiskId).mapFromApi()

        filmInBd?.let {
            // film = it.mapFromBd()
            detailMovie.favorite = it.favorite
            detailMovie.seeLater = it.seeLater
            detailMovie.watched = it.watched
        } ?: movieDao.save(detailMovie.mapToBd())

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

        return detailMovie
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

    /*   override suspend fun getSerial(kinopoiskId: Int): SeasonsSerial {
           return api.serial(kinopoiskId)
       }*/

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
        addOrDelMovie(id, select, 2)
    }

    override suspend fun watched(id: Int?, select: Boolean) {
        movieDao.updateWatched(id, select)
    }


}

