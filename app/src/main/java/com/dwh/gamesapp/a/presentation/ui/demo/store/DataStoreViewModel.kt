package com.dwh.gamesapp.a.presentation.ui.demo.store

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dwh.gamesapp.core.domain.preferences.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    application: Application
): AndroidViewModel(application) {

    private val _value1 = MutableStateFlow(Data())
    val value1: StateFlow<Data> = _value1

    fun setValues() = viewModelScope.launch(Dispatchers.IO) {
        //dataStoreRepository.setValues(Data("UwU", 80))
    }

    fun getValues() = viewModelScope.launch(Dispatchers.IO) {
        /*dataStoreRepository.getValues().collect {
            withContext(Dispatchers.Main) {
                _value1.value = it
            }
        }*/
    }
}