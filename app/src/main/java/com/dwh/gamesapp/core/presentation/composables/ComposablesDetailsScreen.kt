package com.dwh.gamesapp.core.presentation.composables

import android.text.Html
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import kotlin.math.min

@Composable
fun CoverImageWithBackIconParallaxEffect(
    scrollState: ScrollState,
    imageUrl: String,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {
    SetStatusBarColor(imageUrl)

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
                .data(imageUrl)
                .build(),
            contentDescription = "game genre background image",
            placeholder = painterResource(id = R.drawable.image_controller_placeholder),
            error = painterResource(id = R.drawable.image_unavailable_error),
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

@Composable
fun ScrollingTitleComposable(
    scrollState: ScrollState,
    title: String,
) {
    var titleHeightPx by remember { mutableFloatStateOf(0f) }
    var titleWidthPx by remember { mutableFloatStateOf(0f) }

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
        OutlinedTextComposable(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 35.sp),
            strokeWidth = 3f
        )
    }
}

@Composable
fun DescriptionComposable(
    description: String,
    isHeaderDisplayed: Boolean = false
) {
    val formattedDescription = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY).toString()

    if (isHeaderDisplayed) {
        Text(
            text = "About",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.scrim
        )
    }
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = formattedDescription,
        textAlign = TextAlign.Justify,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun SetStatusBarColor(imageUrl: String) {
    /** Utilizado para obtener el tono/color de la imagen y
     * usarlo para el status bar */
    val coroutineScope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()
    var imageBitmap by remember { mutableStateOf(ImageBitmap(1,1)) }
    val imageLoader = ImageLoader(LocalContext.current)
    val target = rememberCoilTarget {bitmap ->
        imageBitmap = bitmap.asImageBitmap()
        coroutineScope.launch {
            val (color, isLight) = bitmap
                .computeDominantTopSectionColor()
            systemUiController.setStatusBarColor(color, isLight)
        }
    }
    val imageRequest = rememberDefaultImageRequest(imageUrl = imageUrl, target = target)
    LaunchedEffect(imageUrl) {
        imageLoader.execute(imageRequest)
    }
}