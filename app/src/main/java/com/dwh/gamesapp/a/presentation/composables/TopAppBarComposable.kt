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
    scrollState: ScrollState = rememberScrollState(),
    headerHeightPx: Float = 0f,
    toolbarHeightPx: Float = 0f,
    iconColor: Color = Color.Transparent,
    topBarColor: Color = Color.Transparent,
    showTopBarColor: Boolean = false,
    title: String = "",
    titleColor: Color = MaterialTheme.colorScheme.background,
    onClickNav: () -> Unit
) {
    val toolbarBottom by remember {
        mutableStateOf(headerHeightPx - toolbarHeightPx)
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
            modifier = if(showTopBarColor) Modifier.background(brush = gradient) else Modifier,
            navigationIcon = {
                IconButton(
                    onClick = { onClickNav() },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "back left icon",
                        tint = iconColor,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            },
            title = {
                Text(text = title, style = MaterialTheme.typography.titleLarge, color = titleColor)
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = topBarColor),
        )
    }
}