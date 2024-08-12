package com.dwh.gamesapp.platforms.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dwh.gamesapp.a.presentation.composables.GameScaffold
import com.dwh.gamesapp.a.presentation.composables.InformationCard
import com.dwh.gamesapp.a.presentation.composables.LoadingAnimation
import com.dwh.gamesapp.core.presentation.composables.CardItemComposable
import com.dwh.gamesapp.core.presentation.navigation.Screens
import com.dwh.gamesapp.platforms.domain.model.Platform

@Composable
fun PlatformScreen(
    navController: NavController,
    viewModel: PlatformViewModel,
    state: PlatformState
) {
    LaunchedEffect(Unit) {
        viewModel.getPlatforms()
    }

    GameScaffold(
        navController = navController,
        isTopBarVisible = true,
        isBottomBarVisible = false,
        showTopBarColor = true,
        title = "Platforms",
        onBackClick = { navController.popBackStack() }
    ) {
        if(state.isLoading) {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        } else {
            PlatformView(navController = navController, state = state)
        }
    }
}

@Composable
private fun PlatformView(
    navController: NavController,
    state: PlatformState
) {
    if(state.platforms.isNotEmpty()) {
        VerticalGridPlatforms(navController = navController, platforms = state.platforms)
    } else {
        InformationCard(
            modifier = Modifier.fillMaxSize(),
            message = state.errorMessage,
            description = state.errorDescription
        )
    }
}


@Composable
private fun VerticalGridPlatforms(
    navController: NavController,
    platforms: List<Platform>
) {
    val listState = rememberLazyGridState()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(
            items = platforms,
            key ={ platform -> platform.id ?: 0}
        ) { platform ->
            PlatformItem(
                name = platform.name ?: "N/A",
                imageBackground = platform.imageBackground ?: "",
                gamesCount = platform.gamesCount ?: 0
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
        name = name,
        imageBackground = imageBackground,
        gamesCount = gamesCount,
        onClick = onClick
    )
}
