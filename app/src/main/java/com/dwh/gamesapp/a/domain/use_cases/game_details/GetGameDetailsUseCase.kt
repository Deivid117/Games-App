package com.dwh.gamesapp.a.domain.use_cases.game_details

import com.dwh.gamesapp.a.domain.model.game_details.GameDetails
import com.dwh.gamesapp.a.domain.repository.GamesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGameDetailsUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {

    suspend operator fun invoke(id: Int): Flow<GameDetails> {
        return gamesRepository.getGameDetails(id)
    }
}