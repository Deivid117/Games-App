package com.dwh.gamesapp.games.presentation.components

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.dwh.gamesapp.core.presentation.composables.GameFloatingActionButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameButtonToScrollToTop(
    listState: LazyStaggeredGridState,
    listIsNotEmpty: Boolean,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val showScrollToTopButton by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }
    val coroutineScope = rememberCoroutineScope()

    GameFloatingActionButton(
        visible = showScrollToTopButton && listIsNotEmpty,
        onClick = {
            coroutineScope.launch {
                //listState.animateScrollToItem(0)
                listState.scrollToItem(0)
                scrollBehavior.state.heightOffset = 0f
                scrollBehavior.state.contentOffset = 0f
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