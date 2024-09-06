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
import com.dwh.gamesapp.core.presentation.composables.GameLoadingAnimation
import com.dwh.gamesapp.core.presentation.composables.DescriptionComposable
import com.dwh.gamesapp.core.presentation.composables.CoverImageWithBackIconParallaxEffect
import com.dwh.gamesapp.core.presentation.composables.ScrollingTitleComposable
import com.dwh.gamesapp.core.presentation.composables.PopularGameItemComposable
import com.dwh.gamesapp.core.presentation.utils.Constants.headerHeight
import com.dwh.gamesapp.core.presentation.utils.LifecycleOwnerListener

@Composable
fun GenreDetailsScreen(
    genreId: Int?,
    genreGames: List<GenreGame>,
    state: GenreDetailsState,
    viewModel: GenreDetailsViewModel,
    onNavigateBack: () -> Unit
) {
    if (genreId != null) {
        LaunchedEffect(viewModel) {
            viewModel.getGenreDetails(genreId)
        }
    }

    GameBackgroundGradient {
        if (state.isLoading) {
            GameLoadingAnimation(modifier = Modifier.fillMaxSize())
        } else {
            GenreDetailsViewWithParallaxEffect(
                genreDetails = state.genreDetails,
                genreGames = genreGames,
                onNavigateBack = onNavigateBack
            )
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
    ) {
        CoverImageWithBackIconParallaxEffect(
            scrollState = scrollState,
            imageUrl = genreDetails?.imageBackground ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
        ) { onNavigateBack() }

        ScrollingTitleComposable(
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

@Composable
private fun GameGenreInformation(
    scrollState: ScrollState,
    genreDetails: GenreDetails?,
    genreGames: List<GenreGame>
) {
    val description = if (genreDetails?.description.isNullOrEmpty()) "N/A" else genreDetails?.description

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

        PopularGamesGenre(genreGames)
    }
}

@Composable
private fun PopularGamesGenre(genreGames: List<GenreGame>) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Popular Games",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onBackground
    )

    Spacer(modifier = Modifier.height(10.dp))

    ListPopularGames(genreGames)
}

@Composable
private fun ListPopularGames(genreGames: List<GenreGame>) {
    genreGames.forEach { game ->
        PopularGameItemComposable(
            gameName = game.name ?: "N/A",
            added = "${(game.added ?: 0)}"
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}