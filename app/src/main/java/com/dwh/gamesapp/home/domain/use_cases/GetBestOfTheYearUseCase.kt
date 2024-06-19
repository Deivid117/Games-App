package com.dwh.gamesapp.home.domain.use_cases

import com.dwh.gamesapp.home.domain.repository.HomeRepository
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.home.domain.model.BestOfTheYearResults
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBestOfTheYearUseCase@Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(dates: String, ordering: String): Flow<Resource<BestOfTheYearResults>> {
        return homeRepository.getBestOfTheYear(dates, ordering)
    }

}