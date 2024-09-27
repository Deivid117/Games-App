package com.dwh.gamesapp.games.domain.repository

import androidx.paging.PagingData
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.favorite_games.domain.model.FavoriteGame
import com.dwh.gamesapp.games.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun getPaginatedGames(): Flow<PagingData<Game>>

    suspend fun searchGames(search: String): Flow<DataState<List<Game>>>
}