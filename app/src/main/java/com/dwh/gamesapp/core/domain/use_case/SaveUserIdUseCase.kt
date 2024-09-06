package com.dwh.gamesapp.core.domain.use_case

import com.dwh.gamesapp.core.domain.preferences.repository.DataStoreRepository
import javax.inject.Inject

class SaveUserIdUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(userId: Long) {
        dataStoreRepository.saveUserId(userId)
    }
}