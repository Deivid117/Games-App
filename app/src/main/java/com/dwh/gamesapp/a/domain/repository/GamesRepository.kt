package com.dwh.gamesapp.a.domain.repository

import com.dwh.gamesapp.a.data.database.entities.GameEntity
import com.dwh.gamesapp.a.domain.model.favorite_game.FavoritGame
import com.dwh.gamesapp.a.domain.model.game.GamesResults
import com.dwh.gamesapp.a.domain.model.game_details.GameDetails
import kotlinx.coroutines.flow.Flow

interface GamesRepository {

    // GET GAMES
    suspend fun getGamesFromApi(page: Int, pageSize: Int): Flow<List<GamesResults>>

    suspend fun getGamesFromDatabase(): Flow<List<GamesResults>>

    suspend fun getAllGames(page: Int, pageSize: Int): Flow<List<GamesResults>>

    // INSERT GAMES ROOM
    suspend fun insertGames(gameEntity: List<GameEntity>)

    // DELETE GAMES ROOM
    suspend fun deleteAllGames()

    // GET GAMES DETAILS
    suspend fun getGameDetailsFromApi(id: Int): Flow<GameDetails>

    suspend fun getGameDetails(id: Int): Flow<GameDetails>

    // ADD FAVORITE GAME
    suspend fun addFavoriteGame(favoriteGame: FavoritGame)

    // GET MY FAVORITES GAMES
    suspend fun getAllFavoritesGames(): Flow<List<FavoritGame>>

    // IS FAVORITE GAME VALIDATION
    suspend fun isMyFavoriteGame(id: Int): Boolean

    // DELETE FAVORITE GAME
    suspend fun removeFromFavoriteGames(id: Int)
}