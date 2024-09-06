package com.dwh.gamesapp.core.presentation.utils.shapes

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class BottomRoundedArcShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawArcPath(size = size)
        )
    }
}

fun drawArcPath(size: Size): Path {
    val arcHeight = size.height / 3

    return Path().apply {
        reset()

        lineTo(size.width, 0f)

        lineTo(size.width, size.height - arcHeight)

        arcTo(
            rect = Rect(
                Offset(0f, size.height - 2 * arcHeight),
                Size(size.width, 2 * arcHeight)
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 180f,
            forceMoveTo = false
        )

        lineTo(0f, 0f)

        close()
    }
}