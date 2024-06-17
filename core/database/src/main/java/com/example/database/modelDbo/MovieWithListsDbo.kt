package com.example.database.modelDbo

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithListsDbo(

    @Embedded
    val movieDbo: MovieDbo,
    @Relation(
        entity = CountriesDbo::class,
        parentColumn = "id",
        entityColumn = "movie_id"
    )
    val countries: List<CountriesDbo>,
    @Relation(
        entity = GenresDbo::class,
        parentColumn = "id",
        entityColumn = "movie_id"
    )
    val genres: List<GenresDbo>

)
