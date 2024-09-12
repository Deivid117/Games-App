package com.dwh.gamesapp.core.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.dwh.gamesapp.edit_profile.presentation.EditProfileScreen
import com.dwh.gamesapp.a.presentation.ui.welcome.WelcomeScreen
import com.dwh.gamesapp.core.presentation.utils.Constants
import com.dwh.gamesapp.edit_profile.navigation.editProfileGraph
import com.dwh.gamesapp.games.navigation.gameGraph
import com.dwh.gamesapp.games_details.navigation.gameDetailsGraph
import com.dwh.gamesapp.genres.navigation.genreGraph
import com.dwh.gamesapp.genres_details.navigation.genreDetailsGraph
import com.dwh.gamesapp.home.navigation.homeGraph
import com.dwh.gamesapp.login.navigation.loginGraph
import com.dwh.gamesapp.platforms.navigation.platformGraph
import com.dwh.gamesapp.platforms_details.navigation.platformDetailsGraph
import com.dwh.gamesapp.profile.navigation.profileGraph
import com.dwh.gamesapp.signup.navigation.registrationGraph

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Navigation(navController: NavController) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = NavigationScreens.Welcome.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(NavigationScreens.Welcome.route,
            exitTransition = {
                fadeOut(animationSpec = tween(1250))
            },
            enterTransition = { EnterTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            WelcomeScreen(navController)
        }

        loginGraph(navController)

        registrationGraph(navController)

        homeGraph(navController)

        navigation(startDestination = NavigationScreens.Profile.route, route = "Profile") {
            profileGraph(navController)

            editProfileGraph(navController)
        }

        /*composable(
            route = Screens.EDIT_PROFILE_SCREEN.name,
            enterTransition = {
                when (initialState.destination.route) {
                    Screens.PROFILE_SCREEN.name -> slideIntoContainer(
                        animationSpec = tween(Constants.ANIMATION_DURATION),
                        towards = AnimatedContentTransitionScope.SlideDirection.Right
                    )

                    else -> null
                }
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(Constants.ANIMATION_DURATION)
                ) + slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(Constants.ANIMATION_DURATION)
                )
            }
        ) {
            EditProfileScreen(navController)
        }*/

        navigation(startDestination = NavigationScreens.Game.route, route = "Games") {
            gameGraph(navController)

            gameDetailsGraph(navController)
        }

        navigation(startDestination = NavigationScreens.Genre.route, route = "Genres") {
            genreGraph(navController)

            genreDetailsGraph(navController)
        }

        navigation(startDestination = NavigationScreens.Platform.route, route = "Platforms") {
            platformGraph(navController)

            platformDetailsGraph(navController)
        }

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