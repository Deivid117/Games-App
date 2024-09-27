package com.dwh.gamesapp.games.domain.use_cases

import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.games.domain.model.Game
import com.dwh.gamesapp.games.domain.repository.GamesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchGamesUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {
    suspend operator fun invoke(search: String): Flow<DataState<List<Game>>> = gamesRepository.searchGames(search)
}