package com.dwh.gamesapp.home.presentation.utils

import android.content.res.Resources
import androidx.compose.runtime.staticCompositionLocalOf

val LocalGameUiInfo = staticCompositionLocalOf { GameUiInfo(
    0f,
    0f,
    0f,
    0f,
    0f
) }

data class GameUiInfo(
    val itemWidthDp: Float,
    val xForCenteredItemDp: Float,
    val xForCenteredItemPx: Float,
    val parallaxOffsetFactor: Float,
    val parallaxOffsetFadeDistancePx: Float,
) {

    companion object {
        private val SCREEN_DENSITY = Resources.getSystem().displayMetrics.density

        fun create(
            screenWidthDp: Float,
            itemWidthDp: Float,
            parallaxOffsetFactor: Float,
            itemWidthFactorForFadeDistance: Float,
        ): GameUiInfo {
            val xForCenteredItemDp = (screenWidthDp - itemWidthDp) / 2
            return GameUiInfo(
                itemWidthDp = itemWidthDp,
                xForCenteredItemDp = xForCenteredItemDp,
                xForCenteredItemPx = xForCenteredItemDp * SCREEN_DENSITY,
                parallaxOffsetFactor = parallaxOffsetFactor,
                parallaxOffsetFadeDistancePx = itemWidthDp * itemWidthFactorForFadeDistance * SCREEN_DENSITY
            )
        }
    }
}