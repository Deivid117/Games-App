package com.dwh.gamesapp.signup.domain.use_case

import com.dwh.gamesapp.a.domain.model.user.User
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.signup.domain.repository.SignupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val signupRepository: SignupRepository,
) {
    suspend operator fun invoke(user: User): Flow<DataState<Long>> =
        signupRepository.registerUser(user)
}