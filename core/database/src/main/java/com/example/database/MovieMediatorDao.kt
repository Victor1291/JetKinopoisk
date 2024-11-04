package com.example.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.modelDbo.MovieMediaDbo

@Dao
interface MovieMediatorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(games: List<MovieMediaDbo>)

    @Query("Select * From movies_media Order By page")
    fun getMovies(): PagingSource<Int, MovieMediaDbo>

    @Query("Delete From movies_media")
    suspend fun clearAllGames()


}