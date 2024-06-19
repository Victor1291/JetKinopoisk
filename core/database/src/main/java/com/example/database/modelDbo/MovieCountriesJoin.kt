package com.example.database.modelDbo

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


@Entity(
    primaryKeys = ["kinopoiskId","country"],
    indices = [Index("kinopoiskId"),Index("country")],
    foreignKeys = [
        ForeignKey(
            entity = MovieDbo::class,
            parentColumns = arrayOf("kinopoiskId"),
            childColumns = arrayOf("kinopoiskId")
        ), ForeignKey(
            entity = CountriesDbo::class,
            parentColumns = arrayOf("country"),
            childColumns = arrayOf("country")
        )
    ]
)
data class MovieCountriesJoin(
    val kinopoiskId: Int,
    val country: String,
)

// У фильма может быть много жанров и стран.
// У стран может быть много фильмов.