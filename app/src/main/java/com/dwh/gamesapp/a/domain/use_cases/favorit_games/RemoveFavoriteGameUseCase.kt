package com.dwh.gamesapp.a.domain.use_cases.favorit_games

import com.dwh.gamesapp.games.domain.repository.GamesRepository
import javax.inject.Inject

class RemoveFavoriteGameUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {

    suspend operator fun invoke(id: Int) {
        gamesRepository.removeFromFavoriteGames(id)
    }
}