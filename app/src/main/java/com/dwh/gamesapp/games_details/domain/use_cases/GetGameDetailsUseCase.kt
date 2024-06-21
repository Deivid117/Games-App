package com.dwh.gamesapp.games_details.domain.use_cases

import com.dwh.gamesapp.games_details.domain.model.GameDetails
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.games_details.domain.repository.GameDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGameDetailsUseCase @Inject constructor(
    private val gameDetailsRepository: GameDetailsRepository
) {
    suspend operator fun invoke(id: Int): Flow<Resource<GameDetails>> {
        return gameDetailsRepository.getGameDetailsFromApi(id)
    }
}