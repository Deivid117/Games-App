package com.dwh.gamesapp.home.domain.use_cases

import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.home.domain.model.NextWeekGames
import com.dwh.gamesapp.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNextWeekGamesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(dates: String, platforms: String): Flow<DataState<List<NextWeekGames>>> {
        return  homeRepository.getNextWeekGames(dates, platforms)
    }
}