package com.dwh.gamesapp.core.presentation.navigation

import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.navigation.Screens.*

sealed class BottomScreens(
    val route: String,
    val title: String,
    val icon: Int
){
    object Home: BottomScreens(
        route = HOME_SCREEN.name,
        title = "Home",
        icon = R.drawable.ic_home_unfilled
    )
    object Games: BottomScreens(
        route = GAME_SCREEN.name,
        title = "Games",
        icon = R.drawable.ic_controller_unfilled
    )
    object Favorites: BottomScreens(
        route = FAVORITE_GAMES_SCREEN.name,
        title = "Favorites",
        icon = R.drawable.ic_heart_filled
    )
    object Profile: BottomScreens(
        route = PROFILE_SCREEN.name,
        title = "Profile",
        icon = R.drawable.ic_user_unfilled
    )
}