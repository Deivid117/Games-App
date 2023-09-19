package com.dwh.gamesapp.presentation.view_model.registration

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.domain.model.user.User
import com.dwh.gamesapp.domain.use_cases.user.AddUserUseCase
import com.dwh.gamesapp.domain.use_cases.user.EmailAlreadyExistsUseCase
import com.dwh.gamesapp.presentation.view_model.games.GamesUiState
import com.dwh.gamesapp.utils.Extensions.isEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.SQLException
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val emailAlreadyExistsUseCase: EmailAlreadyExistsUseCase,
    application: Application
): AndroidViewModel(application) {

    private fun fieldsValidation(
        email: String,
        name: String,
        password: String,
        passwordConfirmation: String
    ) : Boolean {
        var isValid = true

        if (name.isEmpty()) {
            isValid = false
        }
        if(email.isEmpty()) {
            isValid = false
        } else if(!email.isEmail()) {
            isValid = false
        }
        if(password.isEmpty()) {
            isValid = false
        } else if(password != passwordConfirmation && password.isNotEmpty()) {
            isValid = false
        }
        return isValid
    }

    fun registerUser(user: User, success: (Boolean) -> Unit) = viewModelScope.launch {
        try {
            if(fieldsValidation(user.email, user.name, user.password, user.passwordConfirmation ?: "")) {
                val userData = emailAlreadyExistsUseCase(user.email).firstOrNull()
                if(userData != null) {
                    Toast.makeText(getApplication(), "Este correo ya pertenece a otro usuario", Toast.LENGTH_SHORT).show()
                    success(false)
                } else {
                    withContext(Dispatchers.IO) {
                        addUserUseCase(user)
                    }
                    success(true)
                }
            } else {
                Toast.makeText(getApplication(), "Verifica los datos ingresados", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("RegisterUser_ViewModel", e.message ?: "Error desconocido")
        }
    }
}