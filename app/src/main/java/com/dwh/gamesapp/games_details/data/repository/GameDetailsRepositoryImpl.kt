package com.dwh.gamesapp.games_details.data.repository

import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.data.map
import com.dwh.gamesapp.core.data.remote.api.GameApiService
import com.dwh.gamesapp.core.data.remote.api.BaseRepo
import com.dwh.gamesapp.games_details.domain.model.GameDetails
import com.dwh.gamesapp.games_details.domain.model.toDomain
import com.dwh.gamesapp.games_details.domain.repository.GameDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GameDetailsRepositoryImpl@Inject constructor(
    private val gameApiService: GameApiService
): GameDetailsRepository, BaseRepo() {
    override suspend fun getGameDetailsFromApi(id: Int): Flow<Resource<GameDetails>> =
        safeApiCall2 {
            gameApiService.getGameDetails(id)
        }.map { resource ->
            resource.map { model -> model.toDomain() }
        }
}