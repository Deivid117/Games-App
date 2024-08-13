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
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.dwh.gamesapp.platforms_details.presentation.PlatformDetailsScreen
import com.dwh.gamesapp.platforms.presentation.PlatformScreen
import com.dwh.gamesapp.a.presentation.ui.profile.EditProfileScreen
import com.dwh.gamesapp.a.presentation.ui.welcome.WelcomeScreen
import com.dwh.gamesapp.core.presentation.navigation.Screens.*
import com.dwh.gamesapp.games.navigation.gameGraph
import com.dwh.gamesapp.games_details.navigation.gameDetailsGraph
import com.dwh.gamesapp.genres.navigation.genreGraph
import com.dwh.gamesapp.genres_details.navigation.genreDetailsGraph
import com.dwh.gamesapp.home.navigation.homeGraph
import com.dwh.gamesapp.platforms.navigation.platformGraph
import com.dwh.gamesapp.platforms.presentation.PlatformViewModel
import com.dwh.gamesapp.platforms_details.navigation.platformDetailsGraph

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Navigation(navController: NavController) {
    val duration = 600
    NavHost(
        navController = navController as NavHostController,
        startDestination = NavigationScreens.Welcome.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(NavigationScreens.Welcome.route) {
            WelcomeScreen(navController)
        }
        /*composable(LOGIN_SCREEN.name) {
            LoginScreen(navController)
        }
        composable(REGISTRATION_SCREEN.name) {
            RegistrationScreen(navController)
        }*/

        homeGraph(navController)

        /*composable(
            route = PROFILE_SCREEN.name,
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) {
            ProfileScreen(navController)
        }*/

        /*composable(
            route = EDIT_PROFILE_SCREEN.name,
            enterTransition = {
                when (initialState.destination.route) {
                    PROFILE_SCREEN.name -> slideIntoContainer(
                        animationSpec = tween(duration),
                        towards = AnimatedContentTransitionScope.SlideDirection.Right
                    )

                    else -> null
                }
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
            EditProfileScreen(navController)
        }*/

        navigation(startDestination = NavigationScreens.Game.route, route = "Games") {
            gameGraph(navController)

            gameDetailsGraph(navController)
        }

        /*composable(
            route = GAME_SCREEN.name,
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(duration)
                )
            }
        ) {
            GamesScreen(navController)
        }*/

        /*composable(
            route = GAME_DETAILS_SCREEN.name + "/{id}",
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
            it.arguments?.getString("id")?.let { id ->
                GameDetailsScreen(navController, id.toInt())
            }
        }*/

        navigation(startDestination = NavigationScreens.Genre.route, route = "Genres") {
            genreGraph(navController)

            genreDetailsGraph(navController)
        }

        navigation(startDestination = NavigationScreens.Platform.route, route = "Platforms") {
            platformGraph(navController)

            platformDetailsGraph(navController)
        }

        // Plataformas
        /*composable(
            route = PLATFORM_SCREEN.name,
            enterTransition = {
                when (initialState.destination.route) {
                    HOME_SCREEN.name -> slideIntoContainer(
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
        }*/

        /*composable(
            route = "${PLATFORM_DETAILS_SCREEN.name}/{platformId}",
            arguments = listOf(navArgument("platformId") { type = NavType.StringType }),
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
        ) { backStackEntry ->
            *//*val games =
            navController.previousBackStackEntry?.savedStateHandle?.get<ArrayList<PlatformGame>>("games")
        if (!games.isNullOrEmpty()) {*//*
            val platformId = backStackEntry.arguments?.getString("platformId")
            PlatformDetailsScreen(navController, platformId)
            //}
        }*/

        // Juegos favoritos
        /*composable(
            route = FAVORITE_GAMES_SCREEN.name,
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(duration)
                )
            }
        ) {
            FavoriteGamesScreen(navController)
        }*/

    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}