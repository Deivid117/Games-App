package com.dwh.gamesapp.home.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.dwh.gamesapp.core.presentation.navigation.NavigationScreens
import com.dwh.gamesapp.home.presentation.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(startDestination = NavigationScreens.Home.route, route = "home") {
        composable(
            route = NavigationScreens.Home.route,
            exitTransition = { fadeOut(animationSpec = tween(700)) }
        ) {
            HomeScreen(navController)
        }
    }
}