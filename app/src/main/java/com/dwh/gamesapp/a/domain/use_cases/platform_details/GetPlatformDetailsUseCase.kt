package com.dwh.gamesapp.a.domain.use_cases.platform_details

import com.dwh.gamesapp.a.domain.model.platform_details.PlatformDetails
import com.dwh.gamesapp.platforms.domain.repository.PlatformsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlatformDetailsUseCase @Inject constructor(
    private val platformsRepository: PlatformsRepository
) {

    suspend operator fun invoke(id: Int) : Flow<PlatformDetails> {
        return platformsRepository.getPlatformDetails(id)
    }
}