package com.dwh.gamesapp.profile.domain.use_case

import com.dwh.gamesapp.core.domain.model.User
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInformationUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(id: Long): Flow<DataState<User?>> = profileRepository.getUserInformation(id)
}