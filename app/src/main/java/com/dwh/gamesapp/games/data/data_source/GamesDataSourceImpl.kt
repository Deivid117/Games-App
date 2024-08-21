package com.dwh.gamesapp.games.data.data_source

import com.dwh.gamesapp.core.data.remote.api.GameApiService
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.games.data.remote.mappers.mapToDomain
import com.dwh.gamesapp.games.domain.model.Game
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GamesDataSourceImpl @Inject constructor(
    private val gameApiService: GameApiService
) : GamesDataSource, BaseRepo() {
    override suspend fun getGames(page: Int, pageSize: Int): Flow<DataState<List<Game>>> {
        return safeApiCall { gameApiService.getGames(page, pageSize) }.map { resultDTODataState ->
            resultDTODataState.mapper { gameDTO -> gameDTO.results.map { it.mapToDomain() } }
        }
    }
}