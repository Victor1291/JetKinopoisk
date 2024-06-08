package com.shu.network.models.gallery_models

import com.google.gson.annotations.SerializedName
import com.shu.models.gallery_models.ListGalleryItems

class ListGalleryItemsDto(
    @SerializedName("total") var total: Int? = null,
    @SerializedName("totalPages") var totalPages: Int? = null,
    @SerializedName("items") var items: List<GalleryItemDto> = listOf()
)

fun ListGalleryItemsDto.toListGalleryItems(): ListGalleryItems {
    return with(this) {
        ListGalleryItems(
            total = total,
            totalPages = totalPages,
            items = items.map { it.toGalleryItem() }
        )
    }
}