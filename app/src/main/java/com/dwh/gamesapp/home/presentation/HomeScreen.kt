@file:OptIn(ExperimentalMaterial3Api::class)

package com.dwh.gamesapp.home.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dwh.gamesapp.core.presentation.composables.GameScaffold
import com.dwh.gamesapp.home.presentation.utils.GameUiInfo
import com.dwh.gamesapp.home.presentation.components.BestOfTheYearHorizontalList
import com.dwh.gamesapp.home.presentation.components.GenresPlatformsCard
import com.dwh.gamesapp.home.presentation.components.NextWeekGamesHorizontalList

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    state: HomeState,
    navigateToGenres: () -> Unit,
    navigateToPlatforms: () -> Unit,
    navigateToGameDetails: (Int) -> Unit
) {
    GameScaffold(
        navController = navController,
        isVisiblePullRefreshIndicator = false
    ) {
        HomeView(
            state = state,
            onClickRefreshNextWeekGames = { viewModel.refreshNextWeekGames() },
            onClickRefreshBestOfTheYear = { viewModel.refreshBestOfTheYear() },
            navigateToGenres = navigateToGenres,
            navigateToPlatforms = navigateToPlatforms,
            navigateToGameDetails = navigateToGameDetails
        )
    }
}

@Composable
private fun HomeView(
    state: HomeState,
    onClickRefreshNextWeekGames: () -> Unit,
    onClickRefreshBestOfTheYear: () -> Unit,
    navigateToGenres: () -> Unit,
    navigateToPlatforms: () -> Unit,
    navigateToGameDetails: (Int) -> Unit
) {
    val gameUiInfo = GameUiInfo.create(
        screenWidthDp = LocalConfiguration.current.screenWidthDp.dp.value,
        itemWidthDp = 170f,
        parallaxOffsetFactor = .33f,
        itemWidthFactorForFadeDistance = .5f
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        GenresPlatformsCard(navigateToGenres = navigateToGenres, navigateToPlatforms = navigateToPlatforms)

        NextWeekGamesHorizontalList(
            state = state,
            gameUiInfo = gameUiInfo,
            onClickRefreshList = onClickRefreshNextWeekGames,
            navigateToGameDetail = navigateToGameDetails
        )

        BestOfTheYearHorizontalList(
            state = state,
            gameUiInfo = gameUiInfo,
            onClickRefreshList = onClickRefreshBestOfTheYear,
            navigateToGameDetail = navigateToGameDetails
        )

        NextWeekGamesHorizontalList(
            state = state,
            gameUiInfo = gameUiInfo,
            onClickRefreshList = onClickRefreshNextWeekGames,
            navigateToGameDetail = navigateToGameDetails
        )

        //TODO: Hacer la lógica para este servicio
        //NewGamesReleasesContent()
    }
}


@Composable
fun NewGamesReleasesValidationResponse(){}

@Composable
fun NewGamesReleasesContent(){}

@Composable
private fun NewGamesReleaseHorizontalList() {}