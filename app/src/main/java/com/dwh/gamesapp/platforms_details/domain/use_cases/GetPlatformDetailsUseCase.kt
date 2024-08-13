package com.dwh.gamesapp.platforms_details.domain.use_cases

import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.platforms_details.domain.model.PlatformDetails
import com.dwh.gamesapp.platforms_details.domain.repository.PlatformDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlatformDetailsUseCase @Inject constructor(
    private val platformDetailsRepository: PlatformDetailsRepository
) {
    suspend operator fun invoke(id: Int) : Flow<DataState<PlatformDetails>> {
        return platformDetailsRepository.getPlatformDetailsFromApi(id)
    }
}