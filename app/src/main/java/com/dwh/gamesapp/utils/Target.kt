package com.dwh.gamesapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import coil.request.ImageRequest
import coil.target.Target

@Composable
fun rememberCoilTarget(onBitmapReady: (Bitmap) -> Unit): Target = remember {
    object : Target {
        override fun onSuccess(result: Drawable) = onBitmapReady(result.toBitmap())
    }
}

object DefaultRequest {
    operator fun invoke(context: Context, url: String, target: coil.target.Target) =
        ImageRequest.Builder(context)
            .data(url)
            .target(target)
            .allowHardware(false)
            .build()
}

@Composable
fun rememberDefaultImageRequest(url: String, target: coil.target.Target): ImageRequest {
    val context = LocalContext.current
    return remember(url) { DefaultRequest(context, url, target) }
}