package com.shu.network.repository

import android.util.Log
import com.shu.models.domain.CollectionsRepository
import com.example.database.MovieDao
import com.example.database.modelDbo.BestMovieDbo
import com.example.database.modelDbo.CollectionsDbo
import com.example.database.modelDbo.CollectionsMovieDbo
import com.example.database.modelDbo.SimilarMovieDbo

import com.shu.models.CinemaItem
import com.shu.models.collections.Collections
import com.shu.network.mapFrom
import com.shu.network.models.mapFromBd
import com.shu.network.toBd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CollectionsRepositoryImpl  @Inject constructor(
    private val movieDao: MovieDao
) : CollectionsRepository {
    override fun getCollection(): Flow<List<Collections>> {
        return movieDao.getCollections().map { list ->
            list.map { collection ->
                collection.mapFromBd()
            }
        }
    }

    override fun getCollection(filmId: Int): Flow<List<Collections>> {
        return movieDao.getCollectionsList().map { list ->
            list.map { collection ->
                collection.mapFromBd()
            }
        }
    }

    override suspend fun getCheckedCollections(filmId: Int) : List<Collections> {
        //получаем список коллекций с фильмом
        return movieDao.checkMovie(filmId).map { collection ->
            collection.mapFromBd()
        }
    }

    override suspend fun getInteresting(): List<CinemaItem> {
        return movieDao.getMovieFromInteresting().map {
            it.mapFrom()
        }
    }

    override suspend fun getWatched(): List<CinemaItem> {
        return movieDao.getWatchedMovies().map {
            it.mapFrom()
        }
    }

    override suspend fun getListMovie(collectionId: Int): List<CinemaItem> {
        return movieDao.getFromCollection(collectionId).map {
            it.mapFrom()
        }
    }

    /* override suspend fun getListMovie(collectionId: Int): CollectionWithMovie {
         return movieDao.getCollectionMovie()
     }*/


    override suspend fun onAdd(nameCollection: String, icon: Int) {
        //TODO при добавлении новой коллекции , фильм сразу добавляется в эту коллекцию.
        movieDao.addCollection(
            CollectionsDbo(
                name = nameCollection,
                icon = icon,
                checked = true
            )
        )
    }

    /* override suspend fun onUpdate(collections: Collections) {
         movieDao.updateCollection(collections as CollectionsDbo)
     }*/

    override suspend fun onDelete(collections: Collections) {
        movieDao.deleteCollection(collections.toBd())
    }

    //добавление фильма в коллекцию
    override suspend fun addMovieInDb(collectionId: Int, movieId: Int) {
        val answer = movieDao.addMovie(
            CollectionsMovieDbo(
                collectionId = collectionId,
                kinopoiskId = movieId
            )
        )
        //увеличиваем счётчик в total + 1 , если фильм добавился
        if (answer != -1L) {
            movieDao.updateCollection(collectionId)
        }

        //помечаем фильм если фильм добавляем в коллекцию "Любимые"
       /* if (collectionId == 1) {
            movieDao.updateFavorite(movieId, true)
        }
        //помечаем фильм если фильм добавляем в коллекцию "Хочу посмотреть"
        if (collectionId == 2) {
            movieDao.updateSeeLater(movieId, true)
        }
        //Log.d("dao","answer addMovie $answer")
        */
    }

    //удаление фильма из коллекции
    override suspend fun removeMovieInDb(collectionId: Int, movieId: Int) {
         movieDao.deleteMovieFromCollection(collectionId,movieId)

        //если успешно удаление уменьшаем, Delete возвращает число удалёных row
       /* val answer = movieDao.deleteMovieInDB(collectionId, movieId)
        if (answer > 0) {
            movieDao.updateCollectionDel(collectionId)
        }*/
    }


    //TODO сделать открытие всех фильмов похожих и лучших
    override suspend fun addSimilarInDb(similarId: Int, movieId: Int) {
        //TODO очищать список
        movieDao.addSimilar(
            SimilarMovieDbo(
                similarId = similarId,
                kinopoiskId = movieId
            )
        )
    }

    override suspend fun addBestInDb(bestId: Int, movieId: Int) {
        //TODO очищать коллекцию перед сохранением
        movieDao.addBest(
            BestMovieDbo(
                bestId = bestId,
                kinopoiskId = movieId
            )
        )
    }

  /*  override suspend fun clearCollection(collectionId: Int) {
        movieDao.clearCollection(collectionId)
        movieDao.resetCollection(collectionId)
        if (collectionId == FAVORITE) movieDao.clearFavorite()
        if (collectionId == SEE_LATER) movieDao.clearSeeLater()
    }*/

  /*  override suspend fun clearInteresting() {
        movieDao.deleteInteresting()
    }

    override suspend fun clearWatched() {
        movieDao.clearWatched()
    }*/

    companion object {
        const val FAVORITE = 0
        const val SEE_LATER = 1
    }
}