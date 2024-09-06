package com.dwh.gamesapp.a.presentation.ui.welcome

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dwh.gamesapp.core.presentation.composables.GameBackgroundGradient
import com.dwh.gamesapp.a.presentation.view_model.welcome.WelcomeViewModel
import com.dwh.gamesapp.core.presentation.navigation.Screens.*
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    Surface(Modifier.fillMaxSize()) {
        GameBackgroundGradient()
        ValidationRoute(viewModel, navController)
    }
}

@Composable
fun ValidationRoute(
    viewModel: WelcomeViewModel,
    navController: NavController
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.isUserLoggedIn()

        //delay(1000L)
        //navController.navigate(LOGIN_SCREEN.name)

        delay(1000L)
        if (state.isUserLoggedIn) {
            navController.navigate(HOME_SCREEN.name)
        } else {
            navController.navigate(LOGIN_SCREEN.name)
        }
    }
}
