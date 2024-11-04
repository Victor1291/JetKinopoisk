package com.example.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.modelDbo.RemoteKeys
import com.example.database.modelDbo.RemoteKeysPost

@Dao
interface RemoteKeysPostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeysPost>)

    @Query("Select * From post_key Where kinopoiskId = :id")
    suspend fun getRemoteKeyByPostID(id: Int): RemoteKeysPost?

    @Query("Delete From post_key")
    suspend fun clearRemoteKeys()

    @Query("Select created_at From post_key Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

}