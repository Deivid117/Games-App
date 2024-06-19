package com.dwh.gamesapp.a.domain.use_cases.platforms

import com.dwh.gamesapp.a.domain.model.plattform.PlattformResults
import com.dwh.gamesapp.a.domain.repository.PlatformsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlatformsUseCase @Inject constructor(
    private val platformsRepository: PlatformsRepository
) {

    suspend operator fun invoke(): Flow<List<PlattformResults>> {
        return platformsRepository.getAllPlatforms()
    }
}