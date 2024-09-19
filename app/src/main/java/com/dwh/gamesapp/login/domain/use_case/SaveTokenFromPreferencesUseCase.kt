package com.dwh.gamesapp.login.domain.use_case

import com.dwh.gamesapp.core.domain.preferences.repository.DataStoreRepository
import javax.inject.Inject

class SaveTokenFromPreferencesUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(token: String) = dataStoreRepository.setToken(token)
}