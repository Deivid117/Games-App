package com.dwh.gamesapp.core.presentation.composables

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
    operator fun invoke(context: Context, url: String, target: Target) =
        ImageRequest.Builder(context)
            .data(url)
            .target(target)
            .allowHardware(false)
            .build()
}

@Composable
fun rememberDefaultImageRequest(imageUrl: String, target: Target): ImageRequest {
    val context = LocalContext.current
    return remember(imageUrl) { DefaultRequest(context, imageUrl, target) }
}