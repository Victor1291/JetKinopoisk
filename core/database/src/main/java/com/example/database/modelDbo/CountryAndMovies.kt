package com.example.database.modelDbo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CountryAndMovies(
    @Embedded
    val country: CountriesDbo,
    @Relation(
        parentColumn = "country",
        entityColumn = "kinopoiskId",
        associateBy = Junction(
            value = MovieCountriesJoin::class,
            parentColumn = "country",
            entityColumn = "kinopoiskId"
        )
    )
    val movies: List<MovieDbo>
)
