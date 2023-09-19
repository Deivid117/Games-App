package com.dwh.gamesapp.data.database.dao

import androidx.room.*
import com.dwh.gamesapp.data.database.entities.FavoriteGameEntity
import com.dwh.gamesapp.domain.model.favorite_game.FavoritGame

@Dao
interface FavoriteGamesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteGame(favoriteGameEntity: FavoriteGameEntity)

    @Query("SELECT * FROM favorite_games_table")
    suspend fun getAllFavoriteGames(): List<FavoriteGameEntity>

    @Query("SELECT * FROM favorite_games_table WHERE id = :id")
    suspend fun isMyFavoriteGame(id: Int): FavoritGame?

    @Query("DELETE FROM favorite_games_table WHERE id = :id")
    suspend fun removeFromFavoriteGames(id: Int)
}