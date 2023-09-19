package com.dwh.gamesapp.presentation.view_model.profile

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.domain.model.user.User
import com.dwh.gamesapp.domain.model.user.UserDataStore
import com.dwh.gamesapp.domain.repository.DataStoreRepository
import com.dwh.gamesapp.domain.use_cases.user.GetUserInfoUseCase
import com.dwh.gamesapp.presentation.view_model.games.GamesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.SQLException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val dataStoreRepository: DataStoreRepository,
    application: Application
): AndroidViewModel(application) {

    private val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState.Success(User()))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun getUserInfo() = viewModelScope.launch {
        try {
            getUserInfoUseCase().collect {
                _uiState.value = ProfileUiState.Success(it)
            }
        } catch (e: Exception) {
            _uiState.value = ProfileUiState.Error(e.message ?: "Error desconocido")
            Log.e("GetUserInfo_ViewModel", e.message ?: "Error desconocido")
        }
    }

    fun setUserDataStore(user: UserDataStore) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.setUserData(user)
    }

}