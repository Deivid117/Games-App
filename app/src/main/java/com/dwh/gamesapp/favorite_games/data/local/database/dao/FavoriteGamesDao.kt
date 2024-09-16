package com.dwh.gamesapp.favorite_games.data.local.database.dao

import androidx.room.*
import com.dwh.gamesapp.favorite_games.data.local.database.entities.FavoriteGameEntity
import com.dwh.gamesapp.favorite_games.domain.model.FavoriteGame

@Dao
interface FavoriteGamesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteGame(favoriteGameEntity: FavoriteGameEntity)

    @Query("SELECT * FROM favorite_games_table")
    suspend fun getAllFavoriteGames(): List<FavoriteGameEntity>

    @Query("SELECT * FROM favorite_games_table WHERE id = :id")
    suspend fun isMyFavoriteGame(id: Int): FavoriteGame?

    @Query("DELETE FROM favorite_games_table WHERE id = :id")
    suspend fun deleteFavoriteGame(id: Int)
}