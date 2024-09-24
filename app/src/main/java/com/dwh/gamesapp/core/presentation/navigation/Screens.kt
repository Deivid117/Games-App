package com.dwh.gamesapp.core.presentation.navigation

enum class Screens {
    SPLASH_SCREEN,
    LOGIN_SCREEN,
    SIGNUP_SCREEN,
    HOME_SCREEN,
    PROFILE_SCREEN,
    EDIT_PROFILE_SCREEN,
    GAME_SCREEN,
    GAME_DETAILS_SCREEN,
    GENRE_SCREEN,
    GENRE_DETAILS_SCREEN,
    PLATFORM_SCREEN,
    PLATFORM_DETAILS_SCREEN,
    FAVORITE_GAMES_SCREEN
}

sealed class NavigationScreens(val route: String) {
    object Splash : NavigationScreens(Screens.SPLASH_SCREEN.name)
    object Login : NavigationScreens(Screens.LOGIN_SCREEN.name)
    object Signup : NavigationScreens(Screens.SIGNUP_SCREEN.name)
    object Home : NavigationScreens(Screens.HOME_SCREEN.name)
    object Profile : NavigationScreens(Screens.PROFILE_SCREEN.name)
    object EditProfile : NavigationScreens("${Screens.EDIT_PROFILE_SCREEN.name}/{profileAvatarId}/{profileAvatar}/{userName}")
    object Game : NavigationScreens(Screens.GAME_SCREEN.name)
    object GameDetails : NavigationScreens("${Screens.GAME_DETAILS_SCREEN.name}/{gameId}")
    object Genre : NavigationScreens(Screens.GENRE_SCREEN.name)
    object GenreDetails : NavigationScreens("${Screens.GENRE_DETAILS_SCREEN.name}/{genreId}")
    object Platform : NavigationScreens(Screens.PLATFORM_SCREEN.name)
    object PlatformDetails : NavigationScreens("${Screens.PLATFORM_DETAILS_SCREEN.name}/{platformId}")
    object FavoriteGames : NavigationScreens(Screens.FAVORITE_GAMES_SCREEN.name)
}