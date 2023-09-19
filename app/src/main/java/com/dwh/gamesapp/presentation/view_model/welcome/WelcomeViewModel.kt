package com.dwh.gamesapp.presentation.view_model.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.domain.use_cases.user.FindUserLogged
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val findUserLogged: FindUserLogged
): ViewModel() {

    fun isThereLoggedUser(success: (Boolean) -> Unit) = viewModelScope.launch {
        val userLogged = findUserLogged()
        if(userLogged != null) {
            success(true)
        } else {
            success(false)
        }
    }
}