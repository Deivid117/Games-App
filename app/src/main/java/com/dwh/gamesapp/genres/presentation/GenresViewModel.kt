package com.dwh.gamesapp.genres.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.core.data.Resource
import com.dwh.gamesapp.core.presentation.state.UIState
import com.dwh.gamesapp.genres.domain.model.GenresResults
import com.dwh.gamesapp.genres.domain.use_cases.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase
): ViewModel() {

    private var _uiStateG: MutableStateFlow<UIState<GenresResults?>> = MutableStateFlow(UIState.Loading)
    val uiStateG: MutableStateFlow<UIState<GenresResults?>> get() = _uiStateG

    fun getGenres() = viewModelScope.launch(Dispatchers.IO) {
        getGenresUseCase().collect { resource ->
            _uiStateG.value = when (resource) {
                is Resource.Error -> {
                    Log.e(
                        "ERROR: GENRES",
                        "Error code: ${resource.code} - Message: ${resource.message}"
                    )
                    UIState.Error(resource.message ?: "Error desconocido")
                }
                is Resource.Loading -> UIState.Loading
                is Resource.Success -> UIState.Success(resource.data)
            }
        }
    }
}