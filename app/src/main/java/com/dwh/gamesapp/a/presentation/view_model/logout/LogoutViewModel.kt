package com.dwh.gamesapp.a.presentation.view_model.logout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
): ViewModel() {

    fun userLogout(userId: Long, success: (Boolean) -> Unit) = viewModelScope.launch {

    }

}