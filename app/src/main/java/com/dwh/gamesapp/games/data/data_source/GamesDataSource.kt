package com.dwh.gamesapp.games.data.data_source

import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.games.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GamesDataSource {
    suspend fun getGames(page: Int, pageSize: Int): Flow<DataState<List<Game>>>
}