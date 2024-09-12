package com.dwh.gamesapp.signup.domain.use_case

import com.dwh.gamesapp.core.domain.model.User
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.signup.domain.repository.SignupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAlreadyExistsUseCase @Inject constructor(
    private val signupRepository: SignupRepository
) {
    suspend operator fun invoke(email: String, userName: String): Flow<DataState<User?>> {
        return signupRepository.userAlreadyExists(email, userName)
    }
}