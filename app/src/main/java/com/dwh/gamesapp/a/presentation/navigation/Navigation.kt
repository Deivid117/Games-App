package com.dwh.gamesapp.a.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dwh.gamesapp.a.domain.model.genres.GenreGames
import com.dwh.gamesapp.a.domain.model.plattform.PlattformGames
import com.dwh.gamesapp.home.home.HomeScreen
import com.dwh.gamesapp.a.presentation.ui.favorite_games.FavoriteGamesScreen
import com.dwh.gamesapp.a.presentation.ui.games.GameDetailsScreen
import com.dwh.gamesapp.a.presentation.ui.games.GamesScreen
import com.dwh.gamesapp.a.presentation.ui.genres.GenreDetailsScreen
import com.dwh.gamesapp.a.presentation.ui.genres.GenresScreen
import com.dwh.gamesapp.a.presentation.ui.login.LoginScreen
import com.dwh.gamesapp.a.presentation.ui.platforms.PlatformDetailsScreen
import com.dwh.gamesapp.a.presentation.ui.platforms.PlatformsScreen
import com.dwh.gamesapp.a.presentation.ui.profile.EditProfileScreen
import com.dwh.gamesapp.a.presentation.ui.profile.ProfileScreen
import com.dwh.gamesapp.a.presentation.ui.registration.RegistrationScreen
import com.dwh.gamesapp.a.presentation.ui.welcome.WelcomeScreen

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
            GenresScreen(navController)
        }
        composable(Screens.GENRES_DETAILS_SCREEN + "/{id}") {
            val games =
                navController.previousBackStackEntry?.savedStateHandle?.get<ArrayList<GenreGames>>("games")
            if (!games.isNullOrEmpty()) {
                it.arguments?.getString("id")?.let { id ->
                    GenreDetailsScreen(navController, id.toInt(), games)
                }
            }
        }
        // Plataformas
        composable(Screens.PLATFORMS_SCREEN) {
            PlatformsScreen(navController)
        }
        composable(Screens.PLATFORMS_DETAILS_SCREEN + "/{id}") {
            val games =
                navController.previousBackStackEntry?.savedStateHandle?.get<ArrayList<PlattformGames>>("games")
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
