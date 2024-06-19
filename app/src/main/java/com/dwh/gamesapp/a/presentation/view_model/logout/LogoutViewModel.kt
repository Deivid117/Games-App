package com.dwh.gamesapp.a.presentation.view_model.logout

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.a.domain.use_cases.user.UpdateUserLoggedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.sql.SQLException
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val updateUserLoggedUseCase: UpdateUserLoggedUseCase
): ViewModel() {

    fun userLogout(userId: Long, success: (Boolean) -> Unit) = viewModelScope.launch {
        try {
            updateUserLoggedUseCase(isLogged = false, userId = userId)
            success(true)
        } catch (e: Exception) {
            Log.e("UserLogout_ViewModel",e.message ?: "Error desconocido")
        }
    }

}