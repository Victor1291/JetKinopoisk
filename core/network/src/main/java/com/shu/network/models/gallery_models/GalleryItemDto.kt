package com.shu.network.models.gallery_models

import com.google.gson.annotations.SerializedName
import com.shu.models.gallery_models.GalleryItem

data class GalleryItemDto(
    @SerializedName("imageUrl") var imageUrl: String? = null,
    @SerializedName("previewUrl") var previewUrl: String? = null
)


fun GalleryItemDto.toGalleryItem(): GalleryItem {
    return with(this) {
        GalleryItem(
            imageUrl = imageUrl,
            previewUrl = previewUrl
        )
    }
}