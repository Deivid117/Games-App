package com.dwh.gamesapp.games_details.data.repository

import com.dwh.gamesapp.core.data.remote.api.GameApiService
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.games_details.data.remote.mappers.mapToDomain
import com.dwh.gamesapp.games_details.domain.model.GameDetails
import com.dwh.gamesapp.games_details.domain.repository.GameDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GameDetailsRepositoryImpl@Inject constructor(
    private val gameApiService: GameApiService
): GameDetailsRepository, BaseRepo() {
    override suspend fun getGameDetailsFromApi(id: Int): Flow<DataState<GameDetails>> =
        safeApiCall { gameApiService.getGameDetails(id) }.map { gameDetailsDTODataState ->
            gameDetailsDTODataState.mapper { gameDetailsDTO -> gameDetailsDTO.mapToDomain() }
        }
}