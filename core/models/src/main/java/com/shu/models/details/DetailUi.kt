package com.shu.models.details

import com.shu.models.CinemaItem
import com.shu.models.collections.Collections
import com.shu.models.gallery_models.ListGalleryItems
import com.shu.models.similar_models.ListSimilar

data class DetailUi(
    val film: DetailMovie,
    val actorFilm: List<Actor>,
    val gallery: ListGalleryItems,
    val similarsFilm: ListSimilar,
)
