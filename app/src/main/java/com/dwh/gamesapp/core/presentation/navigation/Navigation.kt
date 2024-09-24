package com.dwh.gamesapp.core.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.dwh.gamesapp.edit_profile.navigation.editProfileGraph
import com.dwh.gamesapp.favorite_games.navigation.favoriteGamesGraph
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
import com.dwh.gamesapp.splash.navigation.splashGraph

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun Navigation(navController: NavController) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = NavigationScreens.Splash.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        splashGraph(navController)

        loginGraph(navController)

        registrationGraph(navController)

        homeGraph(navController)

        navigation(startDestination = NavigationScreens.Profile.route, route = "Profile") {
            profileGraph(navController)

            editProfileGraph(navController)
        }

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

        favoriteGamesGraph(navController)
    }
}