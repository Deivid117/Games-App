package com.dwh.gamesapp.a.domain.use_cases.game_details

import com.dwh.gamesapp.a.domain.model.favorite_game.FavoritGame
import com.dwh.gamesapp.games.domain.repository.GamesRepository
import javax.inject.Inject

class AddFavoriteGameUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {

    suspend operator fun invoke(favoriteGame: FavoritGame) {
        gamesRepository.addFavoriteGame(favoriteGame)
    }
}