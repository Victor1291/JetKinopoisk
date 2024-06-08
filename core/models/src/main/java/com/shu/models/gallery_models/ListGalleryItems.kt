package com.shu.models.gallery_models

class ListGalleryItems(
    var total: Int? = null,
    var totalPages: Int? = null,
    var items: List<GalleryItem> = listOf()
)