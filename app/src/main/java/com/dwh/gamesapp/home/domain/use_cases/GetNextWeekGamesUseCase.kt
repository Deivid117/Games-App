package com.dwh.gamesapp.home.domain.use_cases

import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.home.domain.model.NextWeekGamesResults
import com.dwh.gamesapp.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNextWeekGamesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(dates: String, platforms: String): Flow<Resource<NextWeekGamesResults>> {
        return  homeRepository.getNextWeekGames(dates, platforms)
    }
}