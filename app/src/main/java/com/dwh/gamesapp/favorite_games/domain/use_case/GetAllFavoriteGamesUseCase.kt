package com.dwh.gamesapp.favorite_games.domain.use_case

import com.dwh.gamesapp.favorite_games.domain.repository.FavoriteGamesRepository
import javax.inject.Inject

class GetAllFavoriteGamesUseCase @Inject constructor(
    private val favoriteGamesRepository: FavoriteGamesRepository
) {
    suspend operator fun invoke() {
        favoriteGamesRepository.getAllFavoriteGames()
    }
}