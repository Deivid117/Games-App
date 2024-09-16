package com.dwh.gamesapp.games_details.domain.use_cases

import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.favorite_games.domain.model.FavoriteGame
import com.dwh.gamesapp.favorite_games.domain.repository.FavoriteGamesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertFavoriteGameUseCase @Inject constructor(
    private val favoriteGamesRepository: FavoriteGamesRepository
) {
    suspend operator fun invoke(favoriteGame: FavoriteGame): Flow<DataState<Unit>> =
        favoriteGamesRepository.insertFavoriteGame(favoriteGame)
}