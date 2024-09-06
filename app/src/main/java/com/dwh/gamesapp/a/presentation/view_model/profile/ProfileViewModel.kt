package com.dwh.gamesapp.a.presentation.view_model.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.a.domain.model.user.User
import com.dwh.gamesapp.a.domain.model.user.UserDataStore
import com.dwh.gamesapp.core.domain.preferences.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    application: Application
): AndroidViewModel(application) {

    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState.Success(
        User()
    ))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun getUserInfo() = viewModelScope.launch {

    }

    fun setUserDataStore(user: UserDataStore) = viewModelScope.launch(Dispatchers.IO) {
    }

}