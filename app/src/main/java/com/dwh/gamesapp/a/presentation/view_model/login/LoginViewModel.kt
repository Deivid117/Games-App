package com.dwh.gamesapp.a.presentation.view_model.login

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.a.domain.model.user.User
import com.dwh.gamesapp.a.domain.use_cases.user.FindUserByEmailUseCase
import com.dwh.gamesapp.a.domain.use_cases.user.UpdateUserLoggedUseCase
import com.dwh.gamesapp.core.presentation.utils.Extensions.isEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val findUserByEmailUseCase: FindUserByEmailUseCase,
    private val updateUserLoggedUseCase: UpdateUserLoggedUseCase,
    application: Application
): AndroidViewModel(application) {

    private fun fieldsValidation(email: String, password: String) : Boolean {
        var isValid = true

        if(email.isEmpty()) {
            isValid = false
        } else if(!email.isEmail()) {
            isValid = false
        }
        if(password.isEmpty()) {
            isValid = false
        }
        return isValid
    }

    fun userLogin(email: String, password: String, success: (Boolean) -> Unit) = viewModelScope.launch {
        try {
            if(fieldsValidation(email, password)) {
                val userExist: User? = findUserByEmailUseCase(email, password)
                if (userExist != null) {
                    Log.d("User", userExist.toString())
                    withContext(Dispatchers.IO) {
                        updateUserLoggedUseCase(isLogged = true, userId = userExist.id)
                    }
                    success(true)
                } else {
                    Toast.makeText(getApplication(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    success(false)
                }
            } else {
                Toast.makeText(getApplication(), "Verifica los datos ingresados", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("UserLogin_ViewModel",e.message ?: "Error desconocido")
        }
    }

}