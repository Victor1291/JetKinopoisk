package com.example.gallery.domain

import androidx.paging.PagingData
import com.shu.models.gallery_models.GalleryItem
import com.shu.models.gallery_models.ListGalleryItems
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {
    suspend fun getGallery(
        filmId: Int,
        type: String
    ): Flow<PagingData<GalleryItem>>

}