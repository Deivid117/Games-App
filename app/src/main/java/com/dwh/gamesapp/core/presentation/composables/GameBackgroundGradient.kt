package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.theme.primary_gradient
import com.dwh.gamesapp.core.presentation.theme.secondary_gradient

@Composable
fun GameBackgroundGradient(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    content: @Composable() (BoxScope.() -> Unit) = {}
) {
    val brushGradientColor = Brush.linearGradient(
        0.0f to primary_gradient,
        500.0f to secondary_gradient,
        start = Offset.Zero,
        end = Offset.Infinite
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = brushGradientColor)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.dark_background_image),
            contentDescription = "background image",
            contentScale = ContentScale.Crop
        )

        Box(modifier = Modifier.padding(paddingValues)) {
            content()
        }
    }
}