package com.dwh.gamesapp.signup.domain.use_case

import com.dwh.gamesapp.core.domain.preferences.repository.DataStoreRepository
import javax.inject.Inject

class SaveBiometricEnabledFromPreferencesUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(isEnabled: Boolean) {
        dataStoreRepository.setBiometricEnabled(isEnabled)
    }
}