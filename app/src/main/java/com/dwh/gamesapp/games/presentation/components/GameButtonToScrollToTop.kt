package com.dwh.gamesapp.games.presentation.components

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.dwh.gamesapp.core.presentation.composables.GameFloatingActionButton
import kotlinx.coroutines.launch

@Composable
fun GameButtonToScrollToTop(
    listState: LazyStaggeredGridState,
    listIsNotEmpty: Boolean
) {
    val showScrollToTopButton by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }
    val coroutineScope = rememberCoroutineScope()

    GameFloatingActionButton(
        visible = showScrollToTopButton && listIsNotEmpty,
        onClick = {
            coroutineScope.launch {
                listState.animateScrollToItem(0)
            }
        },
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = "Arrow up icon",
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}