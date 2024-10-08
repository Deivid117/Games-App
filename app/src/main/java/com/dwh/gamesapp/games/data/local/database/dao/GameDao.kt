package com.dwh.gamesapp.games.data.local.database.dao

import androidx.paging.DataSource
import androidx.room.*
import com.dwh.gamesapp.games.data.local.database.entities.GameEntity

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameEntity>)

    @Query("SELECT * FROM game_table")
    fun getGames(): DataSource.Factory<Int, GameEntity>

    @Query("DELETE FROM game_table")
    suspend fun deleteGames()
}