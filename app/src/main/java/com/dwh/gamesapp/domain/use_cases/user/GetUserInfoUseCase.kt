package com.dwh.gamesapp.domain.use_cases.user

import com.dwh.gamesapp.domain.model.user.User
import com.dwh.gamesapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): Flow<User> {
        return userRepository.getUserInfo()
    }

}