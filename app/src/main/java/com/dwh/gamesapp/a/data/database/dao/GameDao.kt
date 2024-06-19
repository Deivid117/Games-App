package com.dwh.gamesapp.a.data.database.dao

import androidx.room.*
import com.dwh.gamesapp.a.data.database.entities.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(gameEntity: List<GameEntity>)

    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List<GameEntity>

    @Query("SELECT * FROM game_table LIMIT 1")
    fun getGameInfo(): Flow<GameEntity>

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGames()
}