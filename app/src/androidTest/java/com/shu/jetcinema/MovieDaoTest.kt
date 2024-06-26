package com.shu.jetcinema

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.database.MovieDao
import com.example.database.MovieDatabase
import com.example.database.modelDbo.CountriesDbo
import com.example.database.modelDbo.GenresDbo
import com.example.database.modelDbo.MovieDbo
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private lateinit var movieDatabase: MovieDatabase
private lateinit var movieDao: MovieDao

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {
    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        movieDatabase = Room.inMemoryDatabaseBuilder(
            context, MovieDatabase::class.java
        ).build()

        movieDao = movieDatabase.getMovieDao()
    }

    @After
    fun tearDown() {
        movieDatabase.close()
    }

    @Test
    fun save_and_retrieve_movie_data() = runTest {

        // When
        movieDao.save(movieDbo)

        val savedMovie = movieDao.getMovie(id = 1000)

        // Then
        assertEquals(1000, savedMovie?.kinopoiskId)
        assertEquals("nameRu", savedMovie?.nameRu)
        assertEquals("Russia", savedMovie?.countries?.first()?.country)
    }

}

val countriesDbo = CountriesDbo(
    country = "Russia",
    id = 1
)

val genresDbo = GenresDbo (
    genreId = 1,
    genre = "action"
)


val movieDbo = MovieDbo(
    kinopoiskId = 1000,
    nameRu = "nameRu",
    nameEn = "nameEn",
    nameOriginal = "nameOriginal",
    countries = listOf(countriesDbo),
    genres = listOf(genresDbo),
    ratingKinopoisk = "8.0",
    ratingImbd = "9",
    year = "2024",
    type = "MOVIE",
    posterUrl = "posterUrl",
    posterUrlPreview = "posterUrlPreview",
    imdbId = "imdbId",
)





