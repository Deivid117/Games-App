package com.dwh.gamesapp.platforms.presentation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dwh.gamesapp.core.presentation.composables.GameScaffold
import com.dwh.gamesapp.a.presentation.composables.InformationCard
import com.dwh.gamesapp.core.presentation.composables.GameLoadingAnimation
import com.dwh.gamesapp.core.domain.model.ScaleAndAlphaArgs
import com.dwh.gamesapp.core.presentation.composables.GameCard
import com.dwh.gamesapp.core.presentation.navigation.Screens.*
import com.dwh.gamesapp.core.presentation.utils.animations.scaleAndAlpha
import com.dwh.gamesapp.core.presentation.utils.lazygridstate.calculateDelayAndEasing
import com.dwh.gamesapp.platforms.domain.model.Platform
import com.dwh.gamesapp.platforms.domain.model.PlatformGame

@Composable
fun PlatformScreen(
    navController: NavController,
    viewModel: PlatformViewModel,
    state: PlatformState,
) {
    LaunchedEffect(Unit) {
        viewModel.getPlatforms()
    }

    GameScaffold(
        navController = navController,
        isTopBarVisible = true,
        isBottomBarVisible = false,
        showTopBarColor = true,
        topBarTitle = "Platforms",
        onBackClick = { navController.popBackStack() }
    ) {
        if (state.isLoading) {
            GameLoadingAnimation(modifier = Modifier.fillMaxSize())
        } else {
            PlatformView(navController = navController, state = state) { viewModel.setPlatformGames(it) }
        }
    }
}

@Composable
private fun PlatformView(
    navController: NavController,
    state: PlatformState,
    onPlatformClick: (List<PlatformGame>) -> Unit
) {
    if (state.platforms.isNotEmpty()) {
        VerticalGridPlatforms(
            navController = navController,
            platforms = state.platforms,
            onPlatformClick = onPlatformClick
        )
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
    platforms: List<Platform>,
    onPlatformClick: (List<PlatformGame>) -> Unit
) {
    val listState = rememberLazyGridState()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = platforms,
            key = { _, platform -> platform.id ?: 0 }
        ) { index, platform ->

            val (delay, easing) = listState.calculateDelayAndEasing(index = index, columnCount = 2)
            val animation = tween<Float>(durationMillis = 500, delayMillis = delay, easing = easing)
            val args = ScaleAndAlphaArgs(fromScale = 2f, toScale = 1f, fromAlpha = 0f, toAlpha = 1f)
            val (scale, alpha) = scaleAndAlpha(args = args, animation = animation)

            PlatformItem(
                modifier = Modifier.graphicsLayer(alpha = alpha, scaleX = scale, scaleY = scale),
                name = platform.name ?: "N/A",
                imageBackground = platform.imageBackground ?: "",
                gamesCount = platform.gamesCount ?: 0
            ) {
                onPlatformClick(platform.games)
                navController.navigate(PLATFORM_DETAILS_SCREEN.name + "/" + (platform.id ?: 0))
            }
        }
    }
}

@Composable
private fun PlatformItem(
    modifier: Modifier,
    name: String,
    imageBackground: String,
    gamesCount: Int,
    onClick: () -> Unit,
) {
    GameCard(
        modifier = modifier,
        name = name,
        imageBackground = imageBackground,
        gamesCount = gamesCount,
        onClick = onClick
    )
}
