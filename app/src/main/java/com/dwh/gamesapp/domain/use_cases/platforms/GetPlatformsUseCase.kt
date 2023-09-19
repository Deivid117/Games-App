package com.dwh.gamesapp.domain.use_cases.platforms

import com.dwh.gamesapp.domain.model.plattform.PlattformResults
import com.dwh.gamesapp.domain.repository.PlatformsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlatformsUseCase @Inject constructor(
    private val platformsRepository: PlatformsRepository
) {

    suspend operator fun invoke(): Flow<List<PlattformResults>> {
        return platformsRepository.getAllPlatforms()
    }
}