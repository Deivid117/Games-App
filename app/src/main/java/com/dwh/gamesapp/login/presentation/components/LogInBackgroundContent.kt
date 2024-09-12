package com.dwh.gamesapp.login.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.dwh.gamesapp.core.presentation.composables.GameBackgroundGradient
import com.dwh.gamesapp.core.presentation.utils.shapes.BottomRoundedArcShape

@Composable
fun LogInBackgroundContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        GameBackgroundGradient(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .graphicsLayer {
                    clip = true
                    shape = BottomRoundedArcShape()
                }
        ) {
            LogInIconApp()
        }
        Box(modifier = Modifier.fillMaxHeight(0.5f))
    }
}