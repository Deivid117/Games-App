package com.dwh.gamesapp.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.dwh.gamesapp.core.presentation.composables.GameInformationalMessageCard
import com.dwh.gamesapp.home.presentation.HomeState
import com.dwh.gamesapp.home.presentation.utils.GameUiInfo
import com.dwh.gamesapp.home.presentation.utils.LocalGameUiInfo

@Composable
fun BestOfTheYearHorizontalList(
    state: HomeState,
    gameUiInfo: GameUiInfo,
    onClickRefreshList: () -> Unit,
    navigateToGameDetail: (Int) -> Unit
) {
    when {
        ((!state.isLoadingBOTY && state.bestOfTheYear.isEmpty())) -> {
            GameInformationalMessageCard(
                modifier = Modifier.clickable { onClickRefreshList() },
                message = state.errorMessageBOTY,
                description = "${state.errorDescriptionBOTY}\n\nPress to reload"
            )
        }
        else -> {
            CompositionLocalProvider(LocalGameUiInfo provides gameUiInfo) {
                HorizontalListGames(
                    games = state.bestOfTheYear,
                    title = "Best of the year",
                    isLoading = state.isLoadingBOTY,
                    navigateToGameDetails = navigateToGameDetail
                )
            }
        }
    }
}