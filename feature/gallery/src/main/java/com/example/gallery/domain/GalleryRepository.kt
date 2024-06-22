package com.example.gallery.domain

import com.shu.models.gallery_models.ListGalleryItems

interface GalleryRepository {
    suspend fun getGallery(filmId: Int, page: Int, type: String): ListGalleryItems

}