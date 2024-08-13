package com.dwh.gamesapp.platforms_details.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.platforms_details.domain.model.PlatformDetails
import com.dwh.gamesapp.platforms.domain.model.PlatformGame
import com.dwh.gamesapp.a.presentation.composables.BackgroundGradient
import com.dwh.gamesapp.core.presentation.composables.DescriptionComposable
import com.dwh.gamesapp.core.presentation.composables.CoverImageWithBackIconParallaxEffect
import com.dwh.gamesapp.core.presentation.composables.ScrollingTitleComposable
import com.dwh.gamesapp.a.presentation.composables.LoadingAnimation
import com.dwh.gamesapp.core.presentation.composables.PopularGameItemComposable
import com.dwh.gamesapp.core.presentation.utils.Constants.headerHeight
import com.dwh.gamesapp.core.presentation.utils.LifecycleOwnerListener

@Composable
fun PlatformDetailsScreen(
    platformId: Int?,
    platformGames: List<PlatformGame>,
    state: PlatformDetailsState,
    viewModel: PlatformDetailsViewModel,
    onNavigateBack: () -> Unit,
) {
    if (platformId != null) {
        LaunchedEffect(viewModel) {
            viewModel.getPlatformDetails(platformId)
        }
    }

    BackgroundGradient() {
        if (state.isLoading) {
            LoadingAnimation(modifier = Modifier.fillMaxSize())
        } else {
            PlatformDetailsViewWithParallaxEffect(
                platformDetails = state.platformDetails,
                platformGames = platformGames,
                onNavigateBack = onNavigateBack
            )
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
        CoverImageWithBackIconParallaxEffect(
            scrollState = scrollState,
            imageUrl = platformDetails?.imageBackground ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
        ) { onNavigateBack() }

        ScrollingTitleComposable(
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

@Composable
private fun PlatformGameInformation(
    scrollState: ScrollState,
    platformDetails: PlatformDetails?,
    platformGames: List<PlatformGame>
) {
    val description = if (platformDetails?.description.isNullOrEmpty()) "N/A" else platformDetails?.description

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 15.dp)
            .padding(horizontal = 15.dp)
    ) {
        Spacer(Modifier.height(headerHeight - 50.dp))

        DescriptionComposable(description)

        Spacer(modifier = Modifier.height(15.dp))

        PopularGamesPlatform(platformGames)
    }
}

@Composable
private fun PopularGamesPlatform(platformGames: List<PlatformGame>) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Popular Games",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onBackground
    )

    Spacer(modifier = Modifier.height(10.dp))

    ListPopularGames(platformGames)
}

@Composable
private fun ListPopularGames(platformGames: List<PlatformGame>) {
    platformGames.forEach { game ->
        PopularGameItemComposable(
            gameName = game.name ?: "N/A",
            added = "${(game.added ?: 0)}"
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}