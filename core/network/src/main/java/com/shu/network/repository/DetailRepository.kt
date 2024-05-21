package com.shu.network.repository

import com.shu.models.details.DetailMovie

interface DetailRepository {

    suspend fun getFilm(kinopoiskId: Int): DetailMovie

//    suspend fun getActorFilm(kinopoiskId: Int): List<ActorItem>
//
//    suspend fun getSimilarsFilm(kinopoiskId: Int): ListCinema
//
//    suspend fun getGallery(kinopoiskId: Int, type: String, page: Int): GalleryHorizontalItem
//
//    suspend fun getSerial(kinopoiskId: Int): SeasonsSerial
//
//    suspend fun heart(id: Int?, select: Boolean)
//    suspend fun seeLater(id: Int?, select: Boolean)
//    suspend fun watched(id: Int?, select: Boolean)

}