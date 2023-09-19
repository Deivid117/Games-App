package com.dwh.gamesapp.utils

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun Bitmap.computeDominantTopSectionColor(): Pair<Color, Boolean> =
    suspendCancellableCoroutine { continuation ->
        Palette.from(this)
            .setRegion(0,0,this.width,24.dp.value.toInt())
            .maximumColorCount(3)
            .generate { palette ->
                palette ?: continuation.cancel()

                val statusBarColorRgb = palette!!.dominantSwatch?.rgb
                statusBarColorRgb ?: continuation.cancel()

                val hsl = FloatArray(3)
                ColorUtils.colorToHSL(statusBarColorRgb!!, hsl)
                val isLight = hsl[2] >= 0.5
                continuation.resume(Color(statusBarColorRgb) to isLight)
            }
    }