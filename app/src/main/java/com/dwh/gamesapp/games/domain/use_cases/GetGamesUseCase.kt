package com.dwh.gamesapp.games.domain.use_cases

import androidx.paging.PagingData
import com.dwh.gamesapp.games.domain.model.Game
import com.dwh.gamesapp.games.domain.repository.GamesRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {
    suspend operator fun invoke(): Flow<PagingData<Game>> {
        return gamesRepository.getPaginatedGames()
    }
}