package com.dwh.gamesapp.domain.use_cases.platform_details

import com.dwh.gamesapp.domain.model.platform_details.PlatformDetails
import com.dwh.gamesapp.domain.repository.PlatformsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlatformDetailsUseCase @Inject constructor(
    private val platformsRepository: PlatformsRepository
) {

    suspend operator fun invoke(id: Int) : Flow<PlatformDetails> {
        return platformsRepository.getPlatformDetails(id)
    }
}