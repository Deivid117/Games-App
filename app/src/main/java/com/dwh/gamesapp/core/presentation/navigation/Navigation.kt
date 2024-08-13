package com.dwh.gamesapp.core.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
    val duration = 600
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screens.WELCOME,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
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
        composable(
            route = Screens.HOME_SCREEN,
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) {
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
        composable(
            route = Screens.GENRES_SCREEN,
            enterTransition = {
                when (initialState.destination.route) {
                    // Home to Genres, la animación se aplica a Genres
                    Screens.HOME_SCREEN -> slideIntoContainer(
                        animationSpec = tween(duration),
                        towards = AnimatedContentTransitionScope.SlideDirection.Right
                    )
                    // Genres Details back to Genres, la animación se aplica a Genres
                    else -> null
                }
            },
            // Genres to Genres Details, la animación se aplica a Genres
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(duration)
                )
            },
            // Genres back to Home, la animación se aplica a Genres
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(duration)
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(duration)
                )
            }
        ) {
            val viewModel = hiltViewModel<GenreViewModel>()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            GenreScreen(navController, viewModel, state)
        }

        composable(
            route = Screens.GENRES_DETAILS_SCREEN + "/{id}",
            // Genres to Genres Details, la animación se aplica a Genres Details
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(duration),
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                )
            },
            // Genres Details back to Genres, la animación se aplica a Genres Details
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(duration)
                ) + slideOutOfContainer(
                    animationSpec = tween(duration),
                    towards = AnimatedContentTransitionScope.SlideDirection.Up
                )
            }
        ) {
            /*val games =
                navController.previousBackStackEntry?.savedStateHandle?.get<ArrayList<GenreGame>>("games")
            if (!games.isNullOrEmpty()) {*/
            it.arguments?.getString("id")?.let { id ->
                GenreDetailsScreen(navController, id.toInt())
            }
            //}
        }
        // Plataformas
        composable(
            route = Screens.PLATFORMS_SCREEN,
            enterTransition = {
                when (initialState.destination.route) {
                    Screens.HOME_SCREEN -> slideIntoContainer(
                        animationSpec = tween(duration),
                        towards = AnimatedContentTransitionScope.SlideDirection.Right
                    )

                    else -> null
                }
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(duration)
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(duration)
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(duration)
                )
            }
        ) {

            val viewModel = hiltViewModel<PlatformViewModel>()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            PlatformScreen(navController, viewModel, state)
        }

        composable(
            route = Screens.PLATFORMS_DETAILS_SCREEN + "/{id}",
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(duration),
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(duration)
                ) + slideOutOfContainer(
                    animationSpec = tween(duration),
                    towards = AnimatedContentTransitionScope.SlideDirection.Up
                )
            }
        ) {
            /*val games =
                navController.previousBackStackEntry?.savedStateHandle?.get<ArrayList<PlatformGame>>("games")
            if (!games.isNullOrEmpty()) {*/
            it.arguments?.getString("id")?.let { id ->
                PlatformDetailsScreen(navController, id.toInt())
            }
            //}
        }
        // Juegos favoritos
        composable(Screens.FAVORITE_GAMES_SCREEN) {
            FavoriteGamesScreen(navController)
        }
    }
}
