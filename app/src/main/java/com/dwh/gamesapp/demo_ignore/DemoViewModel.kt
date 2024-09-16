package com.dwh.gamesapp.demo_ignore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DemoViewModel : ViewModel() {

    /** Ejemplo usando Live Data **/
    private val _demoLiveData = MutableLiveData<String>()
    val demoLiveData: LiveData<String> = _demoLiveData

    /** Ejemplo usando State Flow **/
    private val _demoStateFlow = MutableStateFlow("")
    val demoStateFlow: StateFlow<String> = _demoStateFlow

    private val _demoStateFlowBoolean = MutableStateFlow(false)
    val demoStateFlowBoolean: StateFlow<Boolean> = _demoStateFlowBoolean

    /** Ejemplo usando UiState para no tener varios flows **/
    private val _demoUiState = MutableStateFlow(UiState())
    val demoUiState: StateFlow<UiState> = _demoUiState.asStateFlow()

    /** Ejemplo usando uistate pero con una sealed class **/
    private val _uiState = MutableStateFlow<DemoUiState>(DemoUiState.Loading)
    val uiState: StateFlow<DemoUiState> = _uiState.asStateFlow()

    fun demoFunction() = viewModelScope.launch {
        // repository.collect { }
        // when
        // guardar los valores
        _demoLiveData.postValue("hola") // live data
        _demoStateFlow.value = "hola" // state flow

        /** Usando uistate **/
        _demoUiState.value = demoUiState.value.copy(loading = true, value = "hola") // primera forma
        _demoUiState.update { it.copy(loading = false, value = "hola") } // forma más sencilla
    }
}