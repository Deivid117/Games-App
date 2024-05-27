package com.dwh.gamesapp.presentation.ui.platforms

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
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
import com.dwh.gamesapp.domain.model.plattform.PlattformResults
import com.dwh.gamesapp.presentation.composables.*
import com.dwh.gamesapp.presentation.view_model.platforms.PlatformsUiState
import com.dwh.gamesapp.presentation.view_model.platforms.PlatformsViewModel
import com.dwh.gamesapp.presentation.navigation.Screens

@Composable
fun PlatformsScreen(
    navController: NavController,
    viewModel: PlatformsViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.getAllPlatforms()
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
        PlatformsValidationResponse(navController, viewModel)
    }
}

@Composable
fun PlatformsValidationResponse(navController: NavController, viewModel: PlatformsViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(uiState) {
        is PlatformsUiState.Error -> {
            val erroMsg = (uiState as PlatformsUiState.Error).errorMessage
            Log.e("PlatformsScreenError", erroMsg)
            EmptyData(
                title = "Sin información disponible",
                description = "No se han encontrado plataformas por el momento, inténtelo más tarde"
            )
        }
        PlatformsUiState.Loading -> {
            LoadingAnimation()
        }
        is PlatformsUiState.Success -> {
            val data = (uiState as PlatformsUiState.Success).data
            PlatformsContent(navController, data)
        }
        else -> {}
    }
}

@Composable
fun PlatformsContent(navController: NavController, platforms: List<PlattformResults>) {
    if(platforms.isNotEmpty()) {
        PlatformsList(platforms, navController)
    } else {
        EmptyData(
            title = "Sin información disponible",
            description = "No se han encontrado juegos por el momento, inténtelo más tarde"
        )
    }
}

@Composable
fun PlatformsList(platforms: List<PlattformResults>, navController: NavController) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(all = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(platforms) {
            PlatformItem(it.name, it.imageBackground, it.gamesCount) {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "games",
                    it.games
                )
                navController.navigate(Screens.PLATFORMS_DETAILS_SCREEN + "/" + it.id)
            }
        }
    }
}

@Composable
fun PlatformItem(name: String, imageBackground: String, gamesCount: Int, onClick: () -> Unit) {
    VerticalGridItem(onClick, imageBackground, name, gamesCount)
}
