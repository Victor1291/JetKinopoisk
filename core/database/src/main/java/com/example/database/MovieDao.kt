package com.example.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.database.modelDbo.BestMovieDbo
import com.example.database.modelDbo.CollectionsDbo
import com.example.database.modelDbo.CollectionsMovieDbo
import com.example.database.modelDbo.CountriesDbo
import com.example.database.modelDbo.GenresDbo
import com.example.database.modelDbo.InterestingMovieDbo
import com.example.database.modelDbo.MovieCountryDbo
import com.example.database.modelDbo.MovieDbo
import com.example.database.modelDbo.MovieGenresDbo
import com.example.database.modelDbo.MovieWithListsDbo
import com.example.database.modelDbo.SimilarMovieDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Transaction
    @Query(value = "SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieDbo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: MovieDbo)

    @Query("DELETE FROM movies WHERE kinopoisk_id = :id")
    suspend fun delete(id: Int)

    @Query(value = "SELECT * FROM movies WHERE movies.watched = 1")
    suspend fun getWatchedMovies(): List<MovieDbo>

    @Transaction
    @Query(value = "SELECT * FROM collections")
    fun getCollections(): Flow<List<CollectionsDbo>>

    @Query(value = "SELECT * FROM collections")
    fun getCollectionsList(): Flow<List<CollectionsDbo>>


    @Query(
        "SELECT * FROM collections" +
                " INNER JOIN collections_movie WHERE kinopoisk_id = :filmId"
    )
    suspend fun checkMovie(filmId: Int): List<CollectionsDbo>


    @Query(
        "SELECT * FROM movies " +
                "INNER JOIN collections_movie ON collections_movie.kinopoisk_id = movies.kinopoisk_id " +
                "INNER JOIN collections ON collections.collection_id = collections_movie.collection_id " +
                "WHERE collections.collection_id = :collectionId"
    )
    fun getMovieFromCollection(collectionId: Int): List<MovieDbo>


    @Query(
        "SELECT * FROM movies " +
                "INNER JOIN collections_movie ON collections_movie.kinopoisk_id = movies.kinopoisk_id " +
                "WHERE collections_movie.collection_id = :collectionId"
    )
    suspend fun getFromCollection(collectionId: Int): List<MovieDbo>

    //TODO сделать вывод списка interesting в обратном порядке не ASC , а DESC?
    @Query(
        "SELECT * FROM movies " +
                "INNER JOIN interesting ON interesting.kinopoisk_id = movies.kinopoisk_id "
    )
    suspend fun getMovieFromInteresting(): List<MovieDbo>

    @Query("SELECT * FROM movies WHERE watched = 1")
    suspend fun getMovieWatched(): List<MovieDbo>

    //TODO сделать снятие флага watched при очистке списка "Просмотрено" getMovieWatched

    /* @Query(value = "SELECT * FROM collections")
     fun getCollectionMovie(): CollectionWithMovieDbo
 */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCollection(collections: CollectionsDbo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCollectionFirst(collections: CollectionsDbo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovie(collections: CollectionsMovieDbo): Long

    @Query("DELETE FROM collections_movie WHERE collections_movie.collection_id = :collectionId AND collections_movie.kinopoisk_id = :movieId ")
    suspend fun addMovieDel(collectionId: Int, movieId: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSimilar(similar: SimilarMovieDbo): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBest(best: BestMovieDbo): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addInterestingMovie(interesting: InterestingMovieDbo)

    @Query("DELETE FROM interesting")
    suspend fun deleteInteresting()

    @Query("DELETE FROM collections_movie WHERE collection_id = :collectionId")
    suspend fun clearCollection(collectionId: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGenres(genres: List<GenresDbo>)

    @Query(value = "SELECT * FROM genres")
    suspend fun getGenres(): List<GenresDbo>

    @Query(value = "SELECT * FROM genres WHERE genres.name LIKE :word ")
    fun getGenresFlow(word: String): Flow<List<GenresDbo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCountry(countries: List<CountriesDbo>)

    @Query(value = "SELECT * FROM countries")
    suspend fun getCountries(): List<CountriesDbo>

    @Query(value = "SELECT * FROM countries WHERE countries.country LIKE :word ")
    fun getCountriesFlow(word: String): Flow<List<CountriesDbo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovieGengres(genres: List<MovieGenresDbo>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovieCountries(countries: List<MovieCountryDbo>)

    @Delete
    suspend fun deleteCollection(collections: CollectionsDbo)

    @Query("UPDATE collections SET total = total + 1 WHERE collection_id = :collectionId ")
    suspend fun updateCollection(collectionId: Int)

    @Query("UPDATE collections SET total = total - 1 WHERE collection_id = :collectionId ")
    suspend fun updateCollectionDel(collectionId: Int)

    @Query("UPDATE collections SET total = 0 WHERE collection_id = :collectionId ")
    suspend fun resetCollection(collectionId: Int)


    @Query("SELECT * FROM movies WHERE kinopoisk_id = :id")
    suspend fun getMovie(id: Int?): MovieDbo?

    //удаляем фильм из коллекции в диалоге BotomSheet
    @Query("DELETE FROM collections_movie WHERE collection_id = :collectionId AND kinopoisk_id = :filmId")
    fun deleteMovieInDB(collectionId: Int, filmId: Int): Int
}