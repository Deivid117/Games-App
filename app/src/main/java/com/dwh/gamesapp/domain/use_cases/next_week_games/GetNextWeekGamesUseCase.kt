package com.dwh.gamesapp.domain.use_cases.next_week_games

import com.dwh.gamesapp.domain.model.game.GamesResults
import com.dwh.gamesapp.domain.model.game.NextWeekGamesResults
import com.dwh.gamesapp.domain.repository.GamesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNextWeekGamesUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
) {
    suspend operator fun invoke(dates: String): Flow<List<NextWeekGamesResults>> {
        return  gamesRepository.getNextWeekGames(dates)
    }
}