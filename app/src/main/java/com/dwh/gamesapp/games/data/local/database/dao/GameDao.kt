package com.dwh.gamesapp.games.data.local.database.dao

import androidx.paging.DataSource
import androidx.room.*
import com.dwh.gamesapp.games.data.local.database.entities.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    // CHIDOS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameEntity>)

    @Query("SELECT * FROM game_table")
    fun getGames(): DataSource.Factory<Int, GameEntity>

    @Query("DELETE FROM game_table")
    suspend fun deleteGames()

    /******************************************************************************************/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(gameEntity: List<GameEntity>)

    @Query("SELECT * FROM game_table")
    suspend fun getAllGames(): List<GameEntity>

    @Query("SELECT * FROM game_table LIMIT 1")
    fun getGameInfo(): Flow<GameEntity>

    @Query("DELETE FROM game_table")
    suspend fun deleteAllGames()
}