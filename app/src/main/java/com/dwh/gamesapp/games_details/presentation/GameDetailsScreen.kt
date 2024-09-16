package com.dwh.gamesapp.games_details.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.games_details.domain.model.GameDetails
import com.dwh.gamesapp.core.presentation.composables.GameBackgroundGradient
import com.dwh.gamesapp.core.presentation.composables.GameInformationalMessageCard
import com.dwh.gamesapp.core.presentation.composables.details.GameAppBarParallaxEffect
import com.dwh.gamesapp.core.presentation.composables.details.ScrollingTitleDetails
import com.dwh.gamesapp.core.presentation.composables.GameLoadingAnimation
import com.dwh.gamesapp.core.presentation.composables.GameScaffold
import com.dwh.gamesapp.core.presentation.theme.dark_green
import com.dwh.gamesapp.core.presentation.theme.light_green
import com.dwh.gamesapp.core.presentation.theme.light_green_background
import com.dwh.gamesapp.core.presentation.utils.Constants.headerHeight
import com.dwh.gamesapp.core.presentation.utils.LifecycleOwnerListener
import com.dwh.gamesapp.core.presentation.utils.isDarkThemeEnabled
import com.dwh.gamesapp.favorite_games.domain.model.FavoriteGame
import com.dwh.gamesapp.games_details.presentation.components.GameInformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailsScreen(
    viewModel: GameDetailsViewModel,
    state: GameDetailsState,
    gameId: String?,
    onNavigateBack: () -> Unit
) {
    GameScaffold(
        isSnackBarVisible = state.isSnackBarVisible,
        isBottomBarVisible = false,
        showBackgroundGradient = false,
        snackBarMessage = state.snackBarMessage,
        lottieAnimationSnackBar = state.lottieAnimationSnackBar,
        snackBarContainerColor = if (isDarkThemeEnabled()) dark_green else light_green_background,
        snackBarBorderColor = light_green,
        onDismissSnackBar = { viewModel.hideSnackBar() }
    ) {
        GameBackgroundGradient(
            isRefreshing = state.isRefreshing,
            onRefresh = { if (gameId != null) viewModel.refreshGameDetails(gameId.toInt()) }
        ) {
            when {
                state.isLoading -> GameLoadingAnimation(modifier = Modifier.fillMaxSize())
                state.isError -> {
                    GameInformationalMessageCard(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        message = state.errorMessage,
                        description = state.errorDescription
                    )
                }
                else -> {
                    GameDetailsView(
                        viewModel = viewModel,
                        gameDetails = state.gameDetails,
                        isMyFavoriteGame = state.isMyFavoriteGame,
                        gameId = gameId ?: "0",
                        onNavigateBack = onNavigateBack
                    )
                }
            }
        }
    }
}

@Composable
private fun GameDetailsView(
    viewModel: GameDetailsViewModel,
    gameDetails: GameDetails?,
    isMyFavoriteGame: Boolean,
    gameId: String,
    onNavigateBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    LifecycleOwnerListener()

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background.copy(.8f))
            .statusBarsPadding()
    ) {
        GameAppBarParallaxEffect(
            scrollState = scrollState,
            imageUrl = gameDetails?.backgroundImageAdditional ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
        ) { onNavigateBack() }

        ScrollingTitleDetails(
            scrollState = scrollState,
            title = gameDetails?.nameOriginal ?: "N/A"
        )

        Box(modifier = Modifier.padding(top = 60.dp)) {
            GameInformation(
                scrollState = scrollState,
                gameDetails = gameDetails,
                isMyFavoriteGame = isMyFavoriteGame,
                addToFavorites = {
                    viewModel.insertFavoriteGame(
                        FavoriteGame(
                            id = gameDetails?.id ?: 0,
                            name = gameDetails?.nameOriginal ?: "N/A",
                            released = gameDetails?.released ?: "N/A",
                            background_image = gameDetails?.backgroundImage ?: "",
                            metacritic = gameDetails?.metacritic ?: 0
                        )
                    )
                },
                deleteFromFavorites = { viewModel.deleteFavoriteGame(gameId.toInt()) }
            )
        }
    }
}