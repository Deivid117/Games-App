package com.dwh.gamesapp.core.domain.use_case

import com.dwh.gamesapp.core.domain.preferences.repository.DataStoreRepository
import javax.inject.Inject

class SaveUserSessionFromPreferencesUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(session: Boolean) {
        dataStoreRepository.saveUserSession(session)
    }
}