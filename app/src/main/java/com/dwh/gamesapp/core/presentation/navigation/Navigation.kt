package com.dwh.gamesapp.core.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dwh.gamesapp.genres.domain.model.GenreGame
import com.dwh.gamesapp.platforms.domain.model.PlatformGame
import com.dwh.gamesapp.home.presentation.HomeScreen
import com.dwh.gamesapp.a.presentation.ui.favorite_games.FavoriteGamesScreen
import com.dwh.gamesapp.games_details.presentation.GameDetailsScreen
import com.dwh.gamesapp.games.presentation.GamesScreen
import com.dwh.gamesapp.genres_details.presentation.GenreDetailsScreen
import com.dwh.gamesapp.genres.presentation.GenreScreen
import com.dwh.gamesapp.a.presentation.ui.login.LoginScreen
import com.dwh.gamesapp.platforms_details.presentation.PlatformDetailsScreen
import com.dwh.gamesapp.platforms.presentation.PlatformScreen
import com.dwh.gamesapp.a.presentation.ui.profile.EditProfileScreen
import com.dwh.gamesapp.a.presentation.ui.profile.ProfileScreen
import com.dwh.gamesapp.a.presentation.ui.registration.RegistrationScreen
import com.dwh.gamesapp.a.presentation.ui.welcome.WelcomeScreen
import com.dwh.gamesapp.genres.presentation.GenreViewModel
import com.dwh.gamesapp.platforms.presentation.PlatformViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Navigation(navController: NavController) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screens.WELCOME
    ) {
        // Pantalla inicial
        composable(Screens.WELCOME) {
            WelcomeScreen(navController)
        }
        // Inicio de sesión
        composable(Screens.LOGIN_SCREEN) {
            LoginScreen(navController)
        }
        //  Registro
        composable(Screens.REGISTRATION_SCREEN) {
            RegistrationScreen(navController)
        }
        // Home
        composable(Screens.HOME_SCREEN) {
            HomeScreen(navController)
        }
        // Perfil
        composable(Screens.PROFILE_SCREEN) {
            ProfileScreen(navController)
        }
        composable(Screens.EDIT_PROFILE_SCREEN) {
            EditProfileScreen(navController)
        }
        // Juegos
        composable(Screens.GAMES_SCREEN) {
            GamesScreen(navController)
        }
        composable(Screens.GAME_DETAILS_SCREEN + "/{id}") {
            it.arguments?.getString("id")?.let { id ->
                GameDetailsScreen(navController, id.toInt())
            }
        }
        // Géneros
        composable(Screens.GENRES_SCREEN) {
            val viewModel = hiltViewModel<GenreViewModel>()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            GenreScreen(navController, viewModel, state)
        }
        composable(Screens.GENRES_DETAILS_SCREEN + "/{id}") {
            val games =
                navController.previousBackStackEntry?.savedStateHandle?.get<ArrayList<GenreGame>>("games")
            if (!games.isNullOrEmpty()) {
                it.arguments?.getString("id")?.let { id ->
                    GenreDetailsScreen(navController, id.toInt(), games)
                }
            }
        }
        // Plataformas
        composable(Screens.PLATFORMS_SCREEN) {

            val viewModel = hiltViewModel<PlatformViewModel>()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            PlatformScreen(navController, viewModel, state)
        }

        composable(Screens.PLATFORMS_DETAILS_SCREEN + "/{id}") {
            val games =
                navController.previousBackStackEntry?.savedStateHandle?.get<ArrayList<PlatformGame>>("games")
            if (!games.isNullOrEmpty()) {
                it.arguments?.getString("id")?.let { id ->
                    PlatformDetailsScreen(navController, id.toInt(), games)
                }
            }
        }
        // Juegos favoritos
        composable(Screens.FAVORITE_GAMES_SCREEN) {
            FavoriteGamesScreen(navController)
        }
    }
}
