package com.dwh.gamesapp.login.domain.use_case

import com.dwh.gamesapp.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        loginRepository.loginUser(email, password)
}