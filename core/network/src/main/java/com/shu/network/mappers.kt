package com.shu.network

import com.example.database.modelDbo.CollectionsDbo
import com.example.database.modelDbo.MovieDbo
import com.shu.models.CinemaItem
import com.shu.models.collections.Collections
import com.shu.network.models.filters.mapFromBd

fun MovieDbo.mapFrom(): CinemaItem {
    return with(this) {
        CinemaItem(
            kinopoiskId = kinopoiskId,
            nameRu = nameRu,
            nameEn = nameEn,
            nameOriginal = nameEn,
            posterUrl = posterUrl,
            posterUrlPreview = posterUrlPreview,
            year = year?.toInt(),
            rating = null,
            countries = countries.map { it.mapFromBd() },
            genres = genres.map { it.mapFromBd() },
            premiereRu = "",
        )
    }
}

fun Collections.toBd(): CollectionsDbo {
    return with(this) {
        CollectionsDbo(
            collectionId = collectionId,
            name = name,
            total = total,
            icon = icon,
            checked = checked,
        )
    }
}
