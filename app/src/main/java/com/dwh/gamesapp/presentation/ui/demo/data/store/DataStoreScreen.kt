package com.dwh.gamesapp.presentation.ui.demo.data.store

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dwh.gamesapp.presentation.view_model.games.GamesViewModel

@Composable
fun DataStoreScreen(
    navController: NavController,
    viewModel: DataStoreViewModel = hiltViewModel(),
    gamesViewModel: GamesViewModel = hiltViewModel()
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    SetValues(lifecycleOwner, viewModel)
    GetValues(lifecycleOwner, viewModel)
}

@Composable
fun SetValues(
    lifecycleOwner: LifecycleOwner,
    viewModel: DataStoreViewModel
) {
    /** SIN VIEWMODEL **/
    /*lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
        val data = Data("hola", 100)
        App.dataStorePreferences.setValues(data)
    }*/

    /** CON VIEWMODEL **/
    viewModel.setValues()
}

@Composable
fun GetValues(lifecycleOwner: LifecycleOwner, viewModel: DataStoreViewModel) {

    /** SIN VIEWMODEL **/
    // Obtenemos los valores en un hilo secundario
    /*var valor by remember { mutableStateOf("") }
    lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
        App.dataStorePreferences.getValues().collect {
            // Para usarlos nos pasamos al hilo principal y poder mostrar esos valores en la ui
            withContext(Dispatchers.Main) {
                valor = it.value1
                Log.w("Valores Data Store", it.value1 + " " + it.value2.toString())
            }
        }
    }*/

    /** CON VIEWMODEL **/
    viewModel.getValues()
    val value by viewModel.value1.collectAsStateWithLifecycle()

    Column() {
        Text(text = value.value1)
        Text(text = value.value2.toString())
    }
}