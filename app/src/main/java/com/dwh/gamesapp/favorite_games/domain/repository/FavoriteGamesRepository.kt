package com.dwh.gamesapp.favorite_games.domain.repository

import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.favorite_games.domain.model.FavoriteGame
import kotlinx.coroutines.flow.Flow

interface FavoriteGamesRepository {
    suspend fun insertFavoriteGame(favoriteGame: FavoriteGame): Flow<DataState<Unit>>

    suspend fun getAllFavoriteGames(): Flow<DataState<List<FavoriteGame>>>

    suspend fun isMyFavoriteGame(gameId: Int): Flow<DataState<Boolean>>

    suspend fun deleteFavoriteGame(gameId: Int): Flow<DataState<Unit>>
}