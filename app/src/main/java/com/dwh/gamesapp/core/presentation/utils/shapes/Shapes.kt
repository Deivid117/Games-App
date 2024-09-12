package com.dwh.gamesapp.core.presentation.utils.shapes

import androidx.compose.foundation.shape.GenericShape
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

fun drawArcPath(size: Size, ): Path {
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

fun wavyShape() = GenericShape { size, _ ->
    val width = size.width
    val height = size.height

    moveTo(0f, height * 0.9f)
    quadraticBezierTo(
        width * 0.25f, height * 0.7f,
        width * 0.5f, height * 0.9f
    )

    quadraticBezierTo(
        width * 0.75f, height * 1.1f,
        width, height * 0.85f
    )

    lineTo(width, 0f)
    lineTo(0f, 0f)

    close()
}


fun Path.roundedRectanglePath(
    size: Size,
    cornerRadius: Float,
    fabRadius: Float,
) {

    val centerX = size.width / 2
    val x0 = centerX - fabRadius * 1.15f
    val y0 = 0f

    val topControlX = x0 + fabRadius * .5f
    val topControlY = y0

    val bottomControlX = x0
    val bottomControlY = y0 + fabRadius

    val firstCurveStart = Offset(x0, y0)

    val firstCurveEnd = Offset(centerX, fabRadius * 1f)

    val firstCurveControlPoint1 = Offset(
        x = topControlX,
        y = topControlY
    )

    val firstCurveControlPoint2 = Offset(
        x = bottomControlX,
        y = bottomControlY
    )

    val secondCurveStart = Offset(
        x = firstCurveEnd.x,
        y = firstCurveEnd.y
    )

    val secondCurveEnd = Offset(
        x = centerX + fabRadius * 1.15f,
        y = 0f
    )

    val secondCurveControlPoint1 = Offset(
        x = secondCurveStart.x + fabRadius,
        y = bottomControlY
    )

    val secondCurveControlPoint2 = Offset(
        x = secondCurveEnd.x - fabRadius / 2,
        y = topControlY
    )

    val radius = cornerRadius * 2

    arcTo(
        rect = Rect(
            left = 0f,
            top = 0f,
            right = radius,
            bottom = radius
        ),
        startAngleDegrees = 180.0f,
        sweepAngleDegrees = 90.0f,
        forceMoveTo = false
    )

    lineTo(x = firstCurveStart.x, y = firstCurveStart.y)

    cubicTo(
        x1 = firstCurveControlPoint1.x,
        y1 = firstCurveControlPoint1.y,
        x2 = firstCurveControlPoint2.x,
        y2 = firstCurveControlPoint2.y,
        x3 = firstCurveEnd.x,
        y3 = firstCurveEnd.y
    )

    cubicTo(
        x1 = secondCurveControlPoint1.x,
        y1 = secondCurveControlPoint1.y,
        x2 = secondCurveControlPoint2.x,
        y2 = secondCurveControlPoint2.y,
        x3 = secondCurveEnd.x,
        y3 = secondCurveEnd.y
    )

    lineTo(x = size.width - cornerRadius, y = 0f)

    arcTo(
        rect = Rect(
            left = size.width - radius,
            top = 0f,
            right = size.width,
            bottom = radius
        ),
        startAngleDegrees = -90.0f,
        sweepAngleDegrees = 90.0f,
        forceMoveTo = false
    )

    lineTo(x = 0f + size.width, y = size.height - cornerRadius)

    arcTo(
        rect = Rect(
            left = size.width - radius,
            top = size.height - radius,
            right = size.width,
            bottom = size.height
        ),
        startAngleDegrees = 0f,
        sweepAngleDegrees = 90.0f,
        forceMoveTo = false
    )

    lineTo(x = cornerRadius, y = size.height)

    arcTo(
        rect = Rect(
            left = 0f,
            top = size.height - radius,
            right = radius,
            bottom = size.height
        ),
        startAngleDegrees = 90.0f,
        sweepAngleDegrees = 90.0f,
        forceMoveTo = false
    )

    lineTo(x = 0f, y = cornerRadius)
    close()
}

class InvertedRoundedShape(
    private val cornerRadius: Float
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(0f, size.height - cornerRadius)
            quadraticBezierTo(
                size.width / 2, size.height + cornerRadius,
                size.width, size.height - cornerRadius
            )
            lineTo(size.width, 0f)
            close()
        }
        return Outline.Generic(path)
    }
}

class OutwardRoundedShape(private val cornerRadius: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(0f, size.height)
            quadraticBezierTo(
                0f, size.height - cornerRadius,
                cornerRadius, size.height - cornerRadius
            )
            lineTo(size.width - cornerRadius, size.height - cornerRadius)
            quadraticBezierTo(
                size.width, size.height - cornerRadius,
                size.width, size.height
            )
            lineTo(size.width, 0f)
            close()
        }
        return Outline.Generic(path)
    }
}