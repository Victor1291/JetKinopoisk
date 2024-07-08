package com.shu.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gallery.domain.GalleryRepository
import com.shu.models.gallery_models.GalleryItem
import com.shu.network.ServiceMovieApi
import com.shu.network.paging.GalleryPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val api: ServiceMovieApi,
) : GalleryRepository {
    override suspend fun getGallery(
        filmId: Int,
        type: String
    ): Flow<PagingData<GalleryItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                GalleryPagingSource(
                    api = api,
                    filmId = filmId,
                    type = type
                )
            }
        ).flow
    }
}