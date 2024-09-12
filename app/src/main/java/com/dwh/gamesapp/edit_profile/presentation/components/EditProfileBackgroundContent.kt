package com.dwh.gamesapp.edit_profile.presentation.components

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
fun EditProfileBackgroundContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        GameBackgroundGradient(
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .graphicsLayer {
                    clip = true
                    shape = BottomRoundedArcShape()
                }
        )
        Box(modifier = Modifier.fillMaxHeight(0.4f))
    }
}