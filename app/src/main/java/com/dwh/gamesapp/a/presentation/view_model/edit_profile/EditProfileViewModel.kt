package com.dwh.gamesapp.a.presentation.view_model.edit_profile

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.a.domain.model.user.User
import com.dwh.gamesapp.a.domain.model.user.UserDataStore
import com.dwh.gamesapp.core.domain.preferences.repository.DataStoreRepository
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

    }

    fun updateUser(user: User, success: (Boolean) -> Unit) = viewModelScope.launch {

    }

}