package com.dwh.gamesapp.domain.use_cases.user

import com.dwh.gamesapp.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserLoggedUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(isLogged: Boolean, userId: Long) {
        userRepository.updateUserLogged(isLogged, userId)
    }

}