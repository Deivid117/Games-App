package com.dwh.gamesapp.games.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dwh.gamesapp.games.data.local.database.entities.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKey(remoteKey: RemoteKeyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(remoteKeys: List<RemoteKeyEntity>)

    @Query("Select * From remote_key_table Where id = :id")
    suspend fun getRemoteKeyByGameId(id: String): RemoteKeyEntity?

    @Query("Delete From remote_key_table")
    suspend fun clearRemoteKeys()
}