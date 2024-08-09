package com.dwh.gamesapp.platforms.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dwh.gamesapp.a.presentation.composables.GameScaffold
import com.dwh.gamesapp.a.presentation.composables.EmptyData
import com.dwh.gamesapp.a.presentation.composables.LoadingAnimation
import com.dwh.gamesapp.core.presentation.composables.CardItemComposable
import com.dwh.gamesapp.core.presentation.navigation.Screens
import com.dwh.gamesapp.platforms.domain.model.Platform

@Composable
fun PlatformsScreen(
    navController: NavController,
    viewModel: PlatformsViewModel,
    state: PlatformsState
) {
    LaunchedEffect(Unit) {
        viewModel.getPlatforms()
    }

    GameScaffold(
        navController,
        isTopBarVisible = true,
        isBottomBarVisible = false,
        showTopBarColor = true,
        title = "Platforms",
        onBackClick = { navController.popBackStack() }
    ) {
        if(state.isLoading) {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        } else {
            PlatformsListContent(navController = navController, state = state)
        }
    }
}

@Composable
private fun PlatformsListContent(
    navController: NavController,
    state: PlatformsState
) {
    if(state.platforms.isNotEmpty()) {
        PlatformsVerticalGrid(navController, state.platforms)
    } else {
        EmptyData(
            modifier = Modifier.fillMaxSize(),
            title = state.errorMessage,
            description = state.errorDescription
        )
    }
}


@Composable
private fun PlatformsVerticalGrid(
    navController: NavController,
    platforms: List<Platform>
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(platforms) { platform ->
            PlatformItem(
                platform.name ?: "N/A",
                platform.imageBackground ?: "",
                platform.gamesCount ?: 0
            ) {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "games",
                    platform.games
                )
                navController.navigate(Screens.PLATFORMS_DETAILS_SCREEN + "/" + (platform.id ?: 0))
            }
        }
    }
}

@Composable
private fun PlatformItem(
    name: String,
    imageBackground: String,
    gamesCount: Int,
    onClick: () -> Unit
) {
    CardItemComposable(
        onClick,
        imageBackground,
        name,
        gamesCount
    )
}
