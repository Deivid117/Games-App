package com.dwh.gamesapp.presentation.ui.welcome

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dwh.gamesapp.presentation.composables.BackgroundGradient
import com.dwh.gamesapp.presentation.view_model.welcome.WelcomeViewModel
import kotlinx.coroutines.delay
import mx.com.satoritech.creditaco.navigation.Screens

@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    Surface(Modifier.fillMaxSize()) {
        BackgroundGradient()
        ValidationRoute(viewModel, navController)
    }
}

@Composable
fun ValidationRoute(
    viewModel: WelcomeViewModel,
    navController: NavController
) {
    LaunchedEffect(viewModel) {
        //delay(3000)
        viewModel.isThereLoggedUser {
            if(it) {
                navController.navigate(Screens.HOME_SCREEN)
            } else {
                navController.navigate(Screens.LOGIN_SCREEN)
            }
        }
    }
}
