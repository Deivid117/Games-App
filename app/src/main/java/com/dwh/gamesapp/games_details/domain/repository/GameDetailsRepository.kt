package com.dwh.gamesapp.games_details.domain.repository

import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.games_details.domain.model.GameDetails
import kotlinx.coroutines.flow.Flow

interface GameDetailsRepository {
    suspend fun getGameDetailsFromApi(id: Int): Flow<Resource<GameDetails>>
}