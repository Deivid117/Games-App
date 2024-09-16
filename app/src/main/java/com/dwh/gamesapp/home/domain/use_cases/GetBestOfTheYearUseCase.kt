package com.dwh.gamesapp.home.domain.use_cases

import com.dwh.gamesapp.home.domain.repository.HomeRepository
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.home.domain.model.BestOfTheYear
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBestOfTheYearUseCase@Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(dates: String, ordering: String): Flow<DataState<List<BestOfTheYear>>> {
        return homeRepository.getBestOfTheYear(dates, ordering)
    }
}