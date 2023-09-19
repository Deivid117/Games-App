package com.dwh.gamesapp.presentation.composables

import android.text.Html
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dwh.gamesapp.R
import com.dwh.gamesapp.utils.Constants.headerHeight
import com.dwh.gamesapp.utils.Constants.paddingMedium
import com.dwh.gamesapp.utils.Constants.titleFontScaleEnd
import com.dwh.gamesapp.utils.Constants.titleFontScaleStart
import com.dwh.gamesapp.utils.Constants.titlePaddingEnd
import com.dwh.gamesapp.utils.Constants.titlePaddingStart
import com.dwh.gamesapp.utils.Constants.toolbarHeight
import com.dwh.gamesapp.utils.computeDominantTopSectionColor
import com.dwh.gamesapp.utils.rememberCoilTarget
import com.dwh.gamesapp.utils.rememberDefaultImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import kotlin.math.min

@Composable
fun DetailsHeader(
    scrollState: ScrollState,
    url: String,
    modifier: Modifier = Modifier,
    onClickNav: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val systemUi = rememberSystemUiController()

    var image by remember { mutableStateOf(ImageBitmap(1,1)) }
    val loader = ImageLoader(LocalContext.current)
    val target = rememberCoilTarget {bitmap ->
        image = bitmap.asImageBitmap()
        coroutineScope.launch {
            val (color, isLight) = bitmap
                .computeDominantTopSectionColor()
            systemUi.setStatusBarColor(color, isLight)
        }
    }
    val request = rememberDefaultImageRequest(url = url, target = target)
    LaunchedEffect(url) {
        loader.execute(request)
    }

    Box() {
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
                .data(url,)
                .build(),
            contentDescription = "genre background",
            placeholder = painterResource(id = R.drawable.image_controller),
            error = painterResource(id = R.drawable.image_unavailable),
            contentScale = ContentScale.Crop,
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .clickable { onClickNav() }
                    .background(MaterialTheme.colorScheme.background, CircleShape)
                    .padding(5.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                ,
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "back left icon",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun DetailsTitle(
    scrollState: ScrollState,
    name: String,
) {
    var titleHeightPx by remember { mutableStateOf(0f) }
    var titleWidthPx by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .padding(end = 10.dp)
            .graphicsLayer {
                val collapseRange: Float = (headerHeight.toPx() - toolbarHeight.toPx())
                val collapseFraction: Float = (scrollState.value / collapseRange).coerceIn(0f, 1f)

                val scaleXY = androidx.compose.ui.unit.lerp(
                    titleFontScaleStart.dp,
                    titleFontScaleEnd.dp,
                    collapseFraction
                )

                val titleExtraStartPadding = titleWidthPx.toDp() * (1 - scaleXY.value) / 2f

                val titleYFirstInterpolatedPoint = androidx.compose.ui.unit.lerp(
                    headerHeight - titleHeightPx.toDp() - paddingMedium,
                    headerHeight / 2,
                    collapseFraction
                )

                val titleXFirstInterpolatedPoint = androidx.compose.ui.unit.lerp(
                    titlePaddingStart,
                    (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                    collapseFraction
                )

                val titleYSecondInterpolatedPoint = androidx.compose.ui.unit.lerp(
                    headerHeight / 2,
                    toolbarHeight / 2 - titleHeightPx.toDp() / 2,
                    collapseFraction
                )

                val titleXSecondInterpolatedPoint = androidx.compose.ui.unit.lerp(
                    (titlePaddingEnd - titleExtraStartPadding) * 5 / 4,
                    titlePaddingEnd - titleExtraStartPadding,
                    collapseFraction
                )

                val titleY = androidx.compose.ui.unit.lerp(
                    titleYFirstInterpolatedPoint,
                    titleYSecondInterpolatedPoint,
                    collapseFraction
                )

                val titleX = androidx.compose.ui.unit.lerp(
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
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 35.sp),
        )
        Text(
            text = name,
            color = MaterialTheme.colorScheme.background,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 35.sp,
                drawStyle = Stroke(
                    miter = 10f,
                    width = 3f,
                    join = StrokeJoin.Round
                )
            )
        )
    }
}

@Composable
fun DetailsDescription(description: String, showTitle: Boolean = false) {
    val descriptionFormated = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY).toString()

    if(showTitle) {
        Text(
            text = "About",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = descriptionFormated,
        textAlign = TextAlign.Justify,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun LifecycleOwnerListener() {
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    val systemUi = rememberSystemUiController()
    val isSystemDarkTheme = isSystemInDarkTheme()
    val colorStatusBar = MaterialTheme.colorScheme.primary

    DisposableEffect(Unit) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> {
                    coroutineScope.launch {
                        systemUi.setStatusBarColor(colorStatusBar, isSystemDarkTheme)
                    }
                }
                Lifecycle.Event.ON_DESTROY -> {
                    coroutineScope.launch {
                        systemUi.setStatusBarColor(colorStatusBar, isSystemDarkTheme)
                    }
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}