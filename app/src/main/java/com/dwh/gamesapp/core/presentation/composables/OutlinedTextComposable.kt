package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun OutlinedTextComposable(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    strokeWidth: Float = 1f,
    textAlign: TextAlign = TextAlign.Left,
    textDecoration: TextDecoration = TextDecoration.None
) {
    Box() {
        Text(
            modifier = modifier,
            text = text,
            style = style,
            textAlign = textAlign,
            textDecoration = textDecoration,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            modifier = modifier,
            text = text,
            textAlign = textAlign,
            color = MaterialTheme.colorScheme.background,
            style = style.copy(
                drawStyle = Stroke(
                    miter = 10f,
                    width = strokeWidth,
                    join = StrokeJoin.Round
                )
            )
        )
    }
}