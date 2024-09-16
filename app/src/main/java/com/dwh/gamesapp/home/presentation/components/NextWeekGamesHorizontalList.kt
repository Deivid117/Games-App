package com.dwh.gamesapp.home.presentation.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.dwh.gamesapp.core.presentation.composables.GameInformationalMessageCard
import com.dwh.gamesapp.home.presentation.HomeState
import com.dwh.gamesapp.home.presentation.utils.GameUiInfo
import com.dwh.gamesapp.home.presentation.utils.LocalGameUiInfo

@Composable
fun NextWeekGamesHorizontalList(
    state: HomeState,
    gameUiInfo: GameUiInfo,
    onClickRefreshList: () -> Unit,
    navigateToGameDetail: (Int) -> Unit
) {
    when {
        (!state.isLoadingNWG && state.nextWeekGames.isEmpty()) -> {
            GameInformationalMessageCard(
                modifier = Modifier.clickable { onClickRefreshList() },
                message = state.errorMessageNWG,
                description = "${state.errorDescriptionNWG}\n\nPress to reload"
            )
        }
        else -> {
            CompositionLocalProvider(LocalGameUiInfo provides gameUiInfo) {
                HorizontalListGames(
                    games = state.nextWeekGames,
                    title = "Next releases",
                    isLoading = state.isLoadingNWG,
                    navigateToGameDetails = navigateToGameDetail
                )
            }
        }
    }
}