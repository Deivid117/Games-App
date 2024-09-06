package com.dwh.gamesapp.a.domain.use_cases.user

import com.dwh.gamesapp.a.domain.model.user.User
import com.dwh.gamesapp.login.domain.repository.LoginRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

}