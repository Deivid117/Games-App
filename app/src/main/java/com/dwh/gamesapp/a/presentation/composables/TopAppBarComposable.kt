package com.dwh.gamesapp.a.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComposable(
    headerHeightPx: Float = 0f,
    toolbarHeightPx: Float = 0f,
    topBarTitle: String = "",
    topBarTitleColor: Color = MaterialTheme.colorScheme.background,
    navigationIconColor: Color = Color.Transparent,
    showTopBarColor: Boolean = false,
    scrollState: ScrollState = rememberScrollState(),
    onBackClick: () -> Unit
) {
    val toolbarBottom by remember {
        mutableFloatStateOf(headerHeightPx - toolbarHeightPx)
    }
    val showToolbar by remember {
        derivedStateOf {
            scrollState.value >= toolbarBottom
        }
    }
    val gradient = Brush.horizontalGradient(
        listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
    )

    AnimatedVisibility(
        visible = showToolbar,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        CenterAlignedTopAppBar(
            modifier = if (showTopBarColor) Modifier.background(brush = gradient) else Modifier,
            navigationIcon = {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "back left icon",
                        tint = navigationIconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            title = {
                Text(
                    text = topBarTitle,
                    style = MaterialTheme.typography.titleLarge,
                    color = topBarTitleColor
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        )
    }
}