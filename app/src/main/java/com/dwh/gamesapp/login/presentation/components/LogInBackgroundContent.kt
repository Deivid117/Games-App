package com.dwh.gamesapp.login.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.GameBackgroundGradient
import com.dwh.gamesapp.core.presentation.theme.primary_gradient
import com.dwh.gamesapp.core.presentation.theme.secondary_gradient
import com.dwh.gamesapp.core.presentation.utils.shapes.BottomRoundedArcShape

@Composable
fun LogInBackgroundContent() {
    val brushGradientColor = Brush.linearGradient(
        0.0f to primary_gradient,
        500.0f to secondary_gradient,
        start = Offset.Zero,
        end = Offset.Infinite
    )

    Column(modifier = Modifier.fillMaxSize()) {
        /*Box(modifier = Modifier
            .background(brush = brushGradientColor)
            .fillMaxHeight(0.5f)
            .graphicsLayer {
                clip = true
                shape = BottomRoundedArcShape()
            }
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.dark_background_image),
                contentDescription = "background image",
                contentScale = ContentScale.Crop
            )
            LogInIconApp()
        }*/
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