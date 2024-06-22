package com.shu.network.repository

import com.example.gallery.domain.GalleryRepository
import com.shu.models.gallery_models.ListGalleryItems
import com.shu.network.ServiceMovieApi
import com.shu.network.models.gallery_models.toListGalleryItems
import javax.inject.Inject

class GalleryRepositoryImpl@Inject constructor(
    private val api: ServiceMovieApi,
): GalleryRepository {
    override suspend fun getGallery(filmId: Int, page: Int, type: String): ListGalleryItems {
        return api.galleryTotal(filmId,page,type).toListGalleryItems()
    }


}