package com.dwh.gamesapp.games.domain.repository

import androidx.paging.PagingData
import com.dwh.gamesapp.favorite_games.domain.model.FavoriteGame
import com.dwh.gamesapp.games.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GamesRepository {

   /** TODO implementacion buena */
    suspend fun getPaginatedGames(): Flow<PagingData<Game>>

    // ADD FAVORITE GAME
    //suspend fun addFavoriteGame(favoriteGame: FavoriteGame)

    // GET MY FAVORITES GAMES
    //suspend fun getAllFavoritesGames(): Flow<List<FavoriteGame>>

    // IS FAVORITE GAME VALIDATION
    //suspend fun isMyFavoriteGame(id: Int): Boolean

    // DELETE FAVORITE GAME
    //suspend fun removeFromFavoriteGames(id: Int)
}