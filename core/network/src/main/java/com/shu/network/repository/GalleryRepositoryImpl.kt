package com.shu.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gallery.domain.GalleryRepository
import com.shu.models.gallery_models.GalleryItem
import com.shu.network.ServiceMovieApi
import com.shu.network.paging.GalleryPagingSource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val api: ServiceMovieApi,
) : GalleryRepository {
    override suspend fun getGallery(
        filmId: Int,
        type: String
    ): Pair<Flow<PagingData<GalleryItem>>, List<Int>> {


        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                GalleryPagingSource(
                    api = api,
                    filmId = filmId,
                    type = type
                )
            }
        ).flow to getTotalList(filmId)
    }


    private suspend fun getTotalList(filmId: Int): List<Int> {

        return coroutineScope {
            val list: List<String> = listOf(
                "STILL",
                "SHOOTING",
                "POSTER",
                "FAN_ART",
                "PROMO",
                "CONCEPT",
                "WALLPAPER",
                "COVER",
                "SCREENSHOT"
            )
            val totalList: MutableList<Deferred<Int>> = mutableListOf()

            list.forEach { type ->
                totalList.add(async { api.galleryTotal(id = filmId, type = type).total ?: 0 })
            }
            return@coroutineScope totalList.awaitAll()
        }
    }

}