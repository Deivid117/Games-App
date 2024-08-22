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
    suspend fun insertRemoteKeys(remoteKeys: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_key_table WHERE id = :id")
    suspend fun getRemoteKeyByGameId(id: String): RemoteKeyEntity?

    @Query("DELETE FROM remote_key_table")
    suspend fun deleteRemoteKeys()
}