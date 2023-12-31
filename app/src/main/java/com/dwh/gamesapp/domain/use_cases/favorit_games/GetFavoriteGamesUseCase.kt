package com.dwh.gamesapp.domain.use_cases.favorit_games

import com.dwh.gamesapp.domain.model.favorite_game.FavoritGame
import com.dwh.gamesapp.domain.repository.GamesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteGamesUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {

    suspend operator fun invoke(): Flow<List<FavoritGame>> {
        return gamesRepository.getAllFavoritesGames()
    }
}