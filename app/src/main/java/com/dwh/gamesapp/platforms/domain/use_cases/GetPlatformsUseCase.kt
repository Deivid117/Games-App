package com.dwh.gamesapp.platforms.domain.use_cases

import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.platforms.domain.model.Platform
import com.dwh.gamesapp.platforms.domain.repository.PlatformsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlatformsUseCase @Inject constructor(
    private val platformsRepository: PlatformsRepository
) {
    suspend operator fun invoke(): Flow<DataState<List<Platform>>> {
        return platformsRepository.getPlatformsFromApi()
    }
}