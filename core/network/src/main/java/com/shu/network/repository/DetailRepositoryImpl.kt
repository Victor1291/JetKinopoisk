package com.shu.network.repository

import com.shu.models.details.DetailMovie
import com.shu.network.ServiceMovieApi
import com.shu.network.modelDetail.mapFrom
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val api: ServiceMovieApi,
) : DetailRepository {

    override suspend fun getFilm(kinopoiskId: Int): DetailMovie {
        return api.getFilm(kinopoiskId).mapFrom()
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

