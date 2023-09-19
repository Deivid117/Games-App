package com.dwh.gamesapp.presentation.view_model.edit_profile

import android.app.Application
import android.text.BoringLayout
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.domain.model.user.User
import com.dwh.gamesapp.domain.model.user.UserDataStore
import com.dwh.gamesapp.domain.repository.DataStoreRepository
import com.dwh.gamesapp.domain.use_cases.user.FindUserLogged
import com.dwh.gamesapp.domain.use_cases.user.UpdateUserUseCase
import com.dwh.gamesapp.presentation.view_model.profile.ProfileUiState
import com.dwh.gamesapp.utils.Extensions.isEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val updateUserUseCase: UpdateUserUseCase,
    private val findUserLogged: FindUserLogged,
    application: Application
): AndroidViewModel(application) {

    private fun fieldsValidation(
        name: String,
        password: String,
        passwordConfirmation: String
    ) : Boolean {
        var isValid = true

        if (name.isEmpty()) {
            isValid = false
        }
        if(password != passwordConfirmation && password.isNotEmpty()) {
            isValid = false
        }
        return isValid
    }

    private val _userData: MutableStateFlow<UserDataStore> = MutableStateFlow(UserDataStore())
    val userData: StateFlow<UserDataStore> = _userData

    fun getValues() = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.getUserData().collect {
            withContext(Dispatchers.Main) {
                _userData.value = it
            }
        }
    }

    fun updateUser(user: User, success: (Boolean) -> Unit) = viewModelScope.launch {
        try {
            val userLogged: User? = findUserLogged()
            if(fieldsValidation(user.name, user.password, user.passwordConfirmation ?: "")) {
                if(userLogged != null) {
                    val userEdited: User = if(user.password.isNotEmpty()) {
                        userLogged.copy(name = user.name, password = user.password, isLogged = _userData.value.isLogged)
                    } else {
                        userLogged.copy(name = user.name, isLogged = _userData.value.isLogged)
                    }
                    updateUserUseCase(
                        userEdited
                    )
                    success(true)
                }
            } else {
                Toast.makeText(getApplication(), "Verifica los datos ingresados", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            success(false)
            Log.e("UpdateUser_ViewModel", e.message ?: "Error desconocido")
        }
    }

}