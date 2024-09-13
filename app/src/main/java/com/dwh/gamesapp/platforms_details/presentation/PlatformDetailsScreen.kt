package com.dwh.gamesapp.platforms_details.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.platforms_details.domain.model.PlatformDetails
import com.dwh.gamesapp.platforms.domain.model.PlatformGame
import com.dwh.gamesapp.core.presentation.composables.GameBackgroundGradient
import com.dwh.gamesapp.core.presentation.composables.GameInformationalMessageCard
import com.dwh.gamesapp.core.presentation.composables.details.GameAppBarParallaxEffect
import com.dwh.gamesapp.core.presentation.composables.details.ScrollingTitleDetails
import com.dwh.gamesapp.core.presentation.composables.GameLoadingAnimation
import com.dwh.gamesapp.core.presentation.utils.Constants.headerHeight
import com.dwh.gamesapp.core.presentation.utils.LifecycleOwnerListener
import com.dwh.gamesapp.platforms_details.presentation.components.PlatformGameInformation

@Composable
fun PlatformDetailsScreen(
    viewModel: PlatformDetailsViewModel,
    state: PlatformDetailsState,
    platformGames: List<PlatformGame>,
    platformId: Int?,
    onNavigateBack: () -> Unit
) {
    GameBackgroundGradient(
        isRefreshing = state.isRefreshing,
        onRefresh = { if (platformId != null) viewModel.refreshPlatformDetails(platformId) }
    ) {
        when {
            state.isLoading -> GameLoadingAnimation(modifier = Modifier.fillMaxSize())
            state.isError -> {
                GameInformationalMessageCard(
                    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                    message = state.errorMessage,
                    description = state.errorDescription
                )
            }
            else -> {
                PlatformDetailsViewWithParallaxEffect(
                    platformDetails = state.platformDetails,
                    platformGames = platformGames,
                    onNavigateBack = onNavigateBack
                )
            }
        }
    }
}

@Composable
fun PlatformDetailsViewWithParallaxEffect(
    platformDetails: PlatformDetails?,
    platformGames: List<PlatformGame>,
    onNavigateBack: () -> Unit,
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
            imageUrl = platformDetails?.imageBackground ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
        ) { onNavigateBack() }

        ScrollingTitleDetails(
            scrollState = scrollState,
            title = platformDetails?.name ?: "N/A"
        )

        Box(modifier = Modifier.padding(top = 60.dp)) {
            PlatformGameInformation(
                scrollState = scrollState,
                platformDetails = platformDetails,
                platformGames = platformGames
            )
        }
    }
}