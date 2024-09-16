package com.dwh.gamesapp.favorite_games.domain.use_case

import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.favorite_games.domain.model.FavoriteGame
import com.dwh.gamesapp.favorite_games.domain.repository.FavoriteGamesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteGamesUseCase @Inject constructor(
    private val favoriteGamesRepository: FavoriteGamesRepository
) {
    suspend operator fun invoke(): Flow<DataState<List<FavoriteGame>>> {
        return favoriteGamesRepository.getAllFavoriteGames()
    }
}