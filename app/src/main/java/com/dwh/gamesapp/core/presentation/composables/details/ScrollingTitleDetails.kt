package com.dwh.gamesapp.core.presentation.composables.details

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.dwh.gamesapp.core.presentation.composables.OutlinedText
import com.dwh.gamesapp.core.presentation.utils.Constants

@Composable
fun ScrollingTitleDetails(
    scrollState: ScrollState,
    title: String
) {
    var titleHeightPx by remember { mutableFloatStateOf(0f) }
    var titleWidthPx by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .padding(end = 10.dp)
            .graphicsLayer {
                val collapseRange: Float = (Constants.headerHeight.toPx() - Constants.toolbarHeight.toPx())
                val collapseFraction: Float = (scrollState.value / collapseRange).coerceIn(0f, 1f)
                val scaleXY = lerp(
                    Constants.titleFontScaleStart.dp,
                    Constants.titleFontScaleEnd.dp,
                    collapseFraction
                )
                val titleExtraStartPadding = titleWidthPx.toDp() * (1 - scaleXY.value) / 2f
                val titleYFirstInterpolatedPoint = lerp(
                    Constants.headerHeight - titleHeightPx.toDp() - Constants.paddingMedium,
                    Constants.headerHeight / 2,
                    collapseFraction
                )
                val titleXFirstInterpolatedPoint = lerp(
                    Constants.titlePaddingStart,
                    (Constants.titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                    collapseFraction
                )
                val titleYSecondInterpolatedPoint = lerp(
                    Constants.headerHeight / 2,
                    Constants.toolbarHeight / 2 - titleHeightPx.toDp() / 2,
                    collapseFraction
                )
                val titleXSecondInterpolatedPoint = lerp(
                    (Constants.titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                    Constants.titlePaddingEnd - titleExtraStartPadding,
                    collapseFraction
                )
                val titleY = lerp(
                    titleYFirstInterpolatedPoint,
                    titleYSecondInterpolatedPoint,
                    collapseFraction
                )
                val titleX = lerp(
                    titleXFirstInterpolatedPoint,
                    titleXSecondInterpolatedPoint,
                    collapseFraction
                )

                translationY = titleY.toPx()
                translationX = titleX.toPx()
                scaleX = scaleXY.value
                scaleY = scaleXY.value
            }
            .onGloballyPositioned {
                titleHeightPx = it.size.height.toFloat()
                titleWidthPx = it.size.width.toFloat()
            }
    ) {
        OutlinedText(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 35.sp),
            strokeWidth = 3f
        )
    }
}