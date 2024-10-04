package com.dwh.gamesapp.genres_details.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.genres_details.domain.model.GenreDetails
import com.dwh.gamesapp.genres.domain.model.GenreGame
import com.dwh.gamesapp.core.presentation.composables.GameBackgroundGradient
import com.dwh.gamesapp.core.presentation.composables.GameInformationalMessageCard
import com.dwh.gamesapp.core.presentation.composables.GameLoadingAnimation
import com.dwh.gamesapp.core.presentation.composables.details.DescriptionDetails
import com.dwh.gamesapp.core.presentation.composables.details.GameAppBarParallaxEffect
import com.dwh.gamesapp.core.presentation.composables.details.ScrollingTitleDetails
import com.dwh.gamesapp.core.presentation.composables.PopularGameItemComposable
import com.dwh.gamesapp.core.presentation.utils.Constants.headerHeight
import com.dwh.gamesapp.core.presentation.utils.LifecycleOwnerListener
import com.dwh.gamesapp.genres_details.presentation.components.GameGenreInformation

@Composable
fun GenreDetailsScreen(
    viewModel: GenreDetailsViewModel,
    state: GenreDetailsState,
    genreGames: List<GenreGame>,
    genreId: Int?,
    onNavigateBack: () -> Unit
) {
    GameBackgroundGradient(
        isRefreshing = state.isRefreshing,
        onRefresh = { if (genreId != null) viewModel.refreshGenreDetails(genreId) }
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
                GenreDetailsViewWithParallaxEffect(
                    genreDetails = state.genreDetails,
                    genreGames = genreGames,
                    onNavigateBack = onNavigateBack
                )
            }
        }
    }
}

@Composable
private fun GenreDetailsViewWithParallaxEffect(
    genreDetails: GenreDetails?,
    genreGames: List<GenreGame>,
    onNavigateBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    LifecycleOwnerListener()

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background.copy(.8f))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        GameAppBarParallaxEffect(
            scrollState = scrollState,
            imageUrl = genreDetails?.imageBackground ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
        ) { onNavigateBack() }

        ScrollingTitleDetails(
            scrollState = scrollState,
            title = genreDetails?.name ?: "N/A"
        )

        Box(modifier = Modifier.padding(top = 60.dp)) {
            GameGenreInformation(
                scrollState = scrollState,
                genreDetails = genreDetails,
                genreGames = genreGames
            )
        }
    }
}