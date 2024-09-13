package com.dwh.gamesapp.core.presentation.composables.details

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.rememberCoilTarget
import com.dwh.gamesapp.core.presentation.composables.rememberDefaultImageRequest
import com.dwh.gamesapp.utils.computeDominantTopSectionColor
import kotlinx.coroutines.launch
import kotlin.math.min

@Composable
fun GameAppBarParallaxEffect(
    scrollState: ScrollState,
    imageUrl: String,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {
    setStatusBarColor(imageUrl)

    Box {
        AsyncImage(
            modifier = modifier
                .graphicsLayer {
                    if (scrollState.value > 0) {
                        renderEffect =
                            BlurEffect(scrollState.value * 0.1f, scrollState.value * 0.1f)
                    }
                    alpha = min(1f, 1 - (scrollState.value / 600f))
                    translationY = -scrollState.value.toFloat() / 2f
                },
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .build(),
            contentDescription = "game genre background image",
            placeholder = painterResource(id = R.drawable.image_controller_placeholder),
            error = painterResource(id = R.drawable.image_unavailable_error),
            contentScale = ContentScale.Crop,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .clickable { onNavigateBack() }
                    .background(MaterialTheme.colorScheme.background, CircleShape)
                    .padding(5.dp)
                    .size(24.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "left back icon",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@SuppressLint("ComposableNaming")
@Composable
private fun setStatusBarColor(imageUrl: String) {
    val context = LocalContext.current as ComponentActivity

    val coroutineScope = rememberCoroutineScope()
    //val systemUiController = rememberSystemUiController()
    var imageBitmap by remember { mutableStateOf(ImageBitmap(1, 1)) }
    val imageLoader = ImageLoader(LocalContext.current)
    val target = rememberCoilTarget { bitmap ->
        imageBitmap = bitmap.asImageBitmap()
        coroutineScope.launch {
            val (color, isLight) = bitmap
                .computeDominantTopSectionColor()
            //systemUiController.setStatusBarColor(color, isLight)
            val statusBar = if (isLight) SystemBarStyle.light(
                scrim = color.toArgb(),
                darkScrim = Color.Transparent.toArgb()
            ) else SystemBarStyle.dark(scrim = color.toArgb())
            context.enableEdgeToEdge(statusBarStyle = statusBar)
        }
    }
    val imageRequest = rememberDefaultImageRequest(imageUrl = imageUrl, target = target)
    LaunchedEffect(imageUrl) {
        imageLoader.execute(imageRequest)
    }

    DisposableEffect(Unit) {
        onDispose {
            context.enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb()))
        }
    }
}