package com.shu.models.domain

import com.shu.models.CinemaItem
import com.shu.models.collections.Collections
import kotlinx.coroutines.flow.Flow

interface CollectionsRepository {

    fun getCollection() : Flow<List<Collections>>
    fun getCollection(filmId: Int) : Flow<List<Collections>>
    suspend fun getCheckedCollections(filmId: Int) : List<Collections>

    suspend fun getInteresting() : List<CinemaItem>

    suspend fun getWatched() : List<CinemaItem>

    suspend fun getListMovie(collectionId: Int): List<CinemaItem>

    suspend fun onAdd(nameCollection: String, icon: Int)

    //  suspend fun onUpdate(collections: Collections)

    suspend fun onDelete(collections: Collections)

    suspend fun addMovieInDb(collectionId: Int,movieId: Int)

    suspend fun addSimilarInDb(similarId: Int,movieId: Int)
    suspend fun addBestInDb(bestId: Int,movieId: Int)
   // suspend fun clearInteresting()
    suspend fun removeMovieInDb(collectionId: Int, movieId: Int)
   /* suspend fun clearCollection(collectionId: Int)
    suspend fun clearWatched()*/
   suspend fun clearInteresting()
    suspend fun clearCollection(collectionId: Int)
    suspend fun clearWatched()
}