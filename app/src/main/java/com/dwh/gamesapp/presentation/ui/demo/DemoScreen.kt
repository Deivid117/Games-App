package com.dwh.gamesapp.presentation.ui.demo

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DemoScreen() {
    /** collectAsStateWithLifecycle() prueba a usarlo **/

    val viewModel: DemoViewModel = viewModel()

    /** Forma rápida de obtener el valor en Compose **/
    val liveData by viewModel.demoLiveData.observeAsState()
    val stateFlow by viewModel.demoStateFlow.collectAsState()

    /** Forma en que se obtendría el valor en XML o sin el observeAsState en Compose **/
    val lifecycleOwner = LocalLifecycleOwner.current

    var liveDataObserve by remember { mutableStateOf("") }
    viewModel.demoLiveData.observe(lifecycleOwner) {
        liveDataObserve = it
    }

    var stateFlowCollect by remember { mutableStateOf("") }
    lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.demoStateFlow.collect {
                stateFlowCollect = it
            }
        }
    }

    /** Usando el DemoUIState
     * EJEMPLO 1 **/
    lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiState.collect {
                when (it) {
                    is DemoUiState.Error -> {
                        Log.e("ERROR", it.msg)
                    }
                    DemoUiState.Loading -> {

                    }
                    is DemoUiState.Success -> {
                        Log.w("Success", it.value)
                    }
                }
            }
        }
    }

    /** EJEMPLO 2 **/
    val stateFlowSealedClass by viewModel.uiState.collectAsStateWithLifecycle()
    when(stateFlowSealedClass) {
        is DemoUiState.Error -> {
            Log.e("ERROR", (stateFlowSealedClass as DemoUiState.Error).msg)
        }
        DemoUiState.Loading -> {

        }
        is DemoUiState.Success -> {
            Log.w("Success", (stateFlowSealedClass as DemoUiState.Success).value)
            val uwu = (stateFlowSealedClass as DemoUiState.Success).value
        }
    }

    /*TODO() HAZ UNA DATA CLASS AHÍ PONES TUS VALORES Y CREAS TUS FLOWS
       EN EL VIEW MODEL PARA ACCEDER CON collectAsState EN LA UI
    */
}
