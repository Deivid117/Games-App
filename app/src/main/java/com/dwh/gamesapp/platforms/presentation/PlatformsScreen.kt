package com.dwh.gamesapp.platforms.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dwh.gamesapp.platforms.domain.model.PlatformResults
import com.dwh.gamesapp.a.presentation.composables.BackgroundGradient
import com.dwh.gamesapp.a.presentation.composables.CustomScaffold
import com.dwh.gamesapp.a.presentation.composables.EmptyData
import com.dwh.gamesapp.a.presentation.composables.LoadingAnimation
import com.dwh.gamesapp.core.presentation.composables.CardItemComposable
import com.dwh.gamesapp.a.presentation.navigation.Screens
import com.dwh.gamesapp.core.presentation.state.UIState
import com.dwh.gamesapp.platforms.domain.model.Platform

@Composable
fun PlatformsScreen(
    navController: NavController,
    platformsViewModel: PlatformsViewModel = hiltViewModel()
) {
    LaunchedEffect(platformsViewModel) {
        platformsViewModel.getPlatforms()
    }

    CustomScaffold(
        navController,
        showTopBar = true,
        showBottomBar = false,
        showTopBarColor = true,
        title = "Platforms",
        onBackClick = { navController.popBackStack() }
    ) {
        BackgroundGradient()
        PlatformsContent(navController, platformsViewModel)
    }
}

@Composable
private fun PlatformsContent(
    navController: NavController,
    platformsViewModel: PlatformsViewModel
) {
    Column(Modifier.fillMaxSize()) {
        PlatformsValidationResponse(navController, platformsViewModel)
    }
}

@Composable
private fun PlatformsValidationResponse(
    navController: NavController,
    platformsViewModel: PlatformsViewModel
) {
    val uiState by platformsViewModel.uiStateP.collectAsStateWithLifecycle()

    when(uiState) {
        is UIState.Error -> {
            val errorMsg = (uiState as UIState.Error).errorMessage
            Log.e("ERROR: PlatformsScreen", errorMsg)
            EmptyData(
                title = "Ocurrió un error",
                description = errorMsg
            )
        }

        UIState.Loading -> LoadingAnimation(Modifier.fillMaxSize())

        is UIState.Success -> {
            val platformResults = (uiState as UIState.Success).data
            PlatformsListContent(navController, platformResults)
        }
    }
}

@Composable
private fun PlatformsListContent(
    navController: NavController,
    platformResults: PlatformResults?
) {
    val platforms = platformResults?.results ?: arrayListOf()

    if(platforms.isNotEmpty()) {
        PlatformsVerticalGrid(navController, platforms)
    } else {
        EmptyData(
            title = "Sin información disponible",
            description = "No se han encontrado plataformas por el momento, inténtelo más tarde"
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
