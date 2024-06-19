package com.dwh.gamesapp.a.domain.use_cases.user

import com.dwh.gamesapp.a.domain.model.user.User
import com.dwh.gamesapp.a.domain.repository.UserRepository
import javax.inject.Inject

class FindUserLogged @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): User? {
        return userRepository.findUserLogged()
    }

}