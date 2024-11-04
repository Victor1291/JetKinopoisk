package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.converters.CountryConverters
import com.example.database.converters.GenresConverters
import com.example.database.modelDbo.BestMovieDbo
import com.example.database.modelDbo.CollectionsDbo
import com.example.database.modelDbo.CollectionsMovieDbo
import com.example.database.modelDbo.CountriesDbo
import com.example.database.modelDbo.FiltersDbo
import com.example.database.modelDbo.GenresDbo
import com.example.database.modelDbo.InterestingMovieDbo
import com.example.database.modelDbo.MovieCountriesJoin
import com.example.database.modelDbo.MovieCountryDbo
import com.example.database.modelDbo.MovieDbo
import com.example.database.modelDbo.MovieGenresDbo
import com.example.database.modelDbo.MovieMediaDbo
import com.example.database.modelDbo.RemoteKeys
import com.example.database.modelDbo.SimilarMovieDbo

@Database(
    entities = [
        MovieDbo::class,
        MovieMediaDbo::class,
        MovieCountriesJoin::class,
        CollectionsDbo::class,
        CollectionsMovieDbo::class,
        InterestingMovieDbo::class,
        SimilarMovieDbo::class,
        BestMovieDbo::class,
        GenresDbo::class,
        CountriesDbo::class,
        MovieCountryDbo::class,
        MovieGenresDbo::class,
        FiltersDbo::class,
        RemoteKeys::class,
    ], version = 1, exportSchema = false
)
@TypeConverters(CountryConverters::class, GenresConverters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
    abstract fun getMovieMediatorDao(): MovieMediatorDao

    abstract fun getRemoteKeysDao(): RemoteKeysDao


}
