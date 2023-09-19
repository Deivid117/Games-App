package com.dwh.gamesapp.domain.use_cases.user

import com.dwh.gamesapp.domain.model.user.User
import com.dwh.gamesapp.domain.repository.UserRepository
import javax.inject.Inject

class FindUserByEmailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): User? {
        return userRepository.findUserByEmail(email, password)
    }
}