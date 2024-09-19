package com.dwh.gamesapp.login.domain.use_case

import com.dwh.gamesapp.core.domain.model.EncryptedData
import com.dwh.gamesapp.core.domain.model.User
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUserWithFingerPrintTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(token: EncryptedData): Flow<DataState<User?>> = loginRepository.loginUserWithFingerPrintToken(token)
}