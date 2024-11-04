package com.example.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.modelDbo.MovieMediaDbo
import com.example.database.modelDbo.PostDbo

@Dao
interface PostsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostDbo>)

    @Query("Select * From posts Order By page")
    fun getPosts(): PagingSource<Int, PostDbo>

    @Query("Delete From posts")
    suspend fun clearAllGames()


}