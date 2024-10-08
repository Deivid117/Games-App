package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.theme.top_bar_dark
import com.dwh.gamesapp.core.presentation.theme.top_bar_light
import com.dwh.gamesapp.core.presentation.utils.isDarkThemeEnabled
import com.dwh.gamesapp.core.presentation.utils.shapes.OutwardRoundedShape
import com.dwh.gamesapp.games.presentation.components.ExpandingSearchTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopAppBar(
    headerHeightPx: Float = 0f,
    toolbarHeightPx: Float = 0f,
    title: String = "",
    titleColor: Color = Color.White,
    navigationIconColor: Color = Color.White,
    showTopAppBarColor: Boolean = false,
    showSearchBar: Boolean = false,
    scrollState: ScrollState = rememberScrollState(),
    scrollBehavior: TopAppBarScrollBehavior,
    listState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    searchText: String = "",
    onSearchText: (String) -> Unit = {},
    onClickSearchGames: (String) -> Unit = {},
    onClickClearTextField: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val topAppBarColor = if (isDarkThemeEnabled()) top_bar_dark else top_bar_light
    val topAppBarBottom by remember {
        mutableFloatStateOf(headerHeightPx - toolbarHeightPx)
    }
    val isTopAppBarVisible by remember {
        derivedStateOf {
            scrollState.value >= topAppBarBottom
        }
    }

    AnimatedVisibility(
        visible = isTopAppBarVisible,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        CenterAlignedTopAppBar(
            modifier =
            if (showTopAppBarColor) Modifier
                .clip(OutwardRoundedShape(80f))
                .background(color = topAppBarColor)
                .padding(bottom = 25.dp)
            else Modifier,
            navigationIcon = {
                if (!showSearchBar) {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "back left icon",
                            tint = navigationIconColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            title = {
                if (showSearchBar) {
                    ExpandingSearchTextField(
                        listState = listState,
                        value = searchText,
                        onValueChange = { onSearchText(it) },
                        onClickSearchGames = { onClickSearchGames(it) },
                        onClickClearTextField = { onClickClearTextField() }
                    )
                } else {
                    Text(text = title, style = MaterialTheme.typography.titleLarge, color = titleColor)
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = if (showTopAppBarColor) topAppBarColor else Color.Transparent,
                scrolledContainerColor = if (showTopAppBarColor) topAppBarColor else Color.Transparent
            ),
            scrollBehavior = scrollBehavior
        )
    }
}