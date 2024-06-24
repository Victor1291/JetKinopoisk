package com.shu.detail_movie.domain

import com.shu.models.details.Actor
import com.shu.models.details.DetailMovie
import com.shu.models.details.DetailUi
import com.shu.models.gallery_models.ListGalleryItems
import com.shu.models.similar_models.ListSimilar

interface DetailRepository {

    suspend fun getDetailUi(kinopoiskId: Int, type: String, page: Int): DetailUi
    suspend fun getFilm(kinopoiskId: Int): DetailMovie

    suspend fun getActorFilm(kinopoiskId: Int): List<Actor>

    suspend fun getSimilarsFilm(kinopoiskId: Int): ListSimilar

    suspend fun getGallery(kinopoiskId: Int, type: String, page: Int): ListGalleryItems

    //suspend fun getSerial(kinopoiskId: Int): SeasonsSerial

    suspend fun heart(id: Int?, select: Boolean)
    suspend fun seeLater(id: Int?, select: Boolean)
    suspend fun watched(id: Int?, select: Boolean)

}