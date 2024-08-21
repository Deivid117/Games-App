package com.dwh.gamesapp.games.domain.repository

import androidx.paging.PagingData
import com.dwh.gamesapp.a.domain.model.favorite_game.FavoritGame
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.games.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GamesRepository {

   /** TODO implementacion buena */
    suspend fun getPaginatedGames(): Flow<PagingData<Game>>

    // ADD FAVORITE GAME
    suspend fun addFavoriteGame(favoriteGame: FavoritGame)

    // GET MY FAVORITES GAMES
    suspend fun getAllFavoritesGames(): Flow<List<FavoritGame>>

    // IS FAVORITE GAME VALIDATION
    suspend fun isMyFavoriteGame(id: Int): Boolean

    // DELETE FAVORITE GAME
    suspend fun removeFromFavoriteGames(id: Int)
}