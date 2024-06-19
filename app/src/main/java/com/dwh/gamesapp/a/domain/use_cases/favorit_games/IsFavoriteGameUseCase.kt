package com.dwh.gamesapp.a.domain.use_cases.favorit_games

import com.dwh.gamesapp.a.domain.repository.GamesRepository
import javax.inject.Inject

class IsFavoriteGameUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {
    suspend operator fun invoke(id: Int): Boolean {
        return gamesRepository.
        isMyFavoriteGame(id)
    }
}