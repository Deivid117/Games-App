package com.dwh.gamesapp.platforms_details.presentation

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dwh.gamesapp.platforms_details.domain.model.PlatformDetails
import com.dwh.gamesapp.platforms.domain.model.PlatformGame
import com.dwh.gamesapp.a.presentation.composables.BackgroundGradient
import com.dwh.gamesapp.core.presentation.composables.DescriptionComposable
import com.dwh.gamesapp.core.presentation.composables.CoverImageWithBackIconParallaxEffect
import com.dwh.gamesapp.core.presentation.composables.ScrollingTitleComposable
import com.dwh.gamesapp.a.presentation.composables.InformationCard
import com.dwh.gamesapp.a.presentation.composables.LoadingAnimation
import com.dwh.gamesapp.core.presentation.composables.PopularGameItemComposable
import com.dwh.gamesapp.core.presentation.state.DataState
import com.dwh.gamesapp.core.presentation.utils.Constants.headerHeight
import com.dwh.gamesapp.core.presentation.utils.Constants.toolbarHeight
import com.dwh.gamesapp.core.presentation.utils.LifecycleOwnerListener
import com.dwh.gamesapp.platforms.presentation.PlatformState

@Composable
fun PlatformDetailsScreen(
    navController: NavController,
    platformId: Int?,
    state: PlatformState,
    viewModel: PlatformDetailsViewModel = hiltViewModel()
) {
    if(platformId != null) {
        LaunchedEffect(viewModel) {
            viewModel.getPlatformDetails(platformId.toInt())
        }
    }

    Surface(Modifier.fillMaxSize()) {
        BackgroundGradient()
        PlatformDetailsContent(viewModel, navController, state.platformGames)
    }
}

@Composable
private fun PlatformDetailsContent(
    viewModel: PlatformDetailsViewModel,
    navController: NavController,
    platformGames: List<PlatformGame>
) {
    PlatformDetailsValidateResponse(
        viewModel,
        navController,
        platformGames
    )
}

@Composable
private fun PlatformDetailsValidateResponse(
    viewModel: PlatformDetailsViewModel,
    navController: NavController,
    platformGames: List<PlatformGame>
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState) {
        is DataState.Error -> {
            val errorMsg = (uiState as DataState.Error).errorMessage
            Log.e("ERROR: PlatformDetailsScreen", errorMsg)
            InformationCard(
                modifier = Modifier.fillMaxSize(),
                message = "Ocurrió un error",
                description = errorMsg
            )
        }
        DataState.Loading -> LoadingAnimation(Modifier.fillMaxSize())

        is DataState.Success -> {
            val platformDetails = (uiState as DataState.Success).data
            PlatformDetailsContentWithParallaxEffect(navController, platformDetails, platformGames)
        }
    }
}

@Composable
fun PlatformDetailsContentWithParallaxEffect(
    navController: NavController,
    platformDetails: PlatformDetails?,
    platformGames: List<PlatformGame>
) {
    val scrollState = rememberScrollState()
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.toPx() }

    LifecycleOwnerListener()

    Box(
        Modifier.background(MaterialTheme.colorScheme.background.copy(.8f))
    ) {
        CoverImageWithBackIconParallaxEffect(
            scrollState = scrollState,
            imageUrl = platformDetails?.imageBackground ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
        ) { navController.popBackStack() }

        ScrollingTitleComposable(
            scrollState = scrollState,
            platformDetails?.name ?: "N/A"
        )

        Box(Modifier.padding(top = 60.dp)) {
            PlatformGameInformation(scrollState, platformDetails, platformGames)
        }

        /*TopAppBarComposable(
            scrollState = scrollState,
            headerHeightPx = headerHeightPx,
            toolbarHeightPx = toolbarHeightPx,
        ) { navController.popBackStack() }*/
    }
}

@Composable
private fun PlatformGameInformation(
    scrollState: ScrollState,
    platformDetails: PlatformDetails?,
    platformGames: List<PlatformGame>
) {
    val description = if(platformDetails?.description.isNullOrEmpty()) "N/A" else platformDetails?.description

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