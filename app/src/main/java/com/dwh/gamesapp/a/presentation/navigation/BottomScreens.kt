package com.dwh.gamesapp.a.presentation.navigation

import com.dwh.gamesapp.R

sealed class BottomScreens(
    val route: String,
    val title: String,
    val icon: Int
){
    object Home: BottomScreens(
        route = Screens.HOME_SCREEN,
        title = "Home",
        icon = R.drawable.ic_home_unfilled
    )
    object Games: BottomScreens(
        route = Screens.GAMES_SCREEN,
        title = "Games",
        icon = R.drawable.ic_controller_unfilled
    )
    object Favorites: BottomScreens(
        route = Screens.FAVORITE_GAMES_SCREEN,
        title = "Favorites",
        icon = R.drawable.ic_heart_filled
    )
    object Profile: BottomScreens(
        route = Screens.PROFILE_SCREEN,
        title = "Profile",
        icon = R.drawable.ic_user_unfilled
    )
}