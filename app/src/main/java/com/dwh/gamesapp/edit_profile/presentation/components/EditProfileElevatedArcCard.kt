package com.dwh.gamesapp.edit_profile.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.theme.image_border
import com.dwh.gamesapp.core.presentation.utils.shapes.roundedRectanglePath

@Composable
fun EditProfileElevatedArcCard(
    modifier: Modifier,
    elevation: Dp = 4.dp,
    image: Int = R.drawable.ic_user_unfilled,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val color = image_border

    val diameter = 90.dp
    val radiusDp = diameter / 2

    val cornerRadiusDp = 10.dp

    val density = LocalDensity.current
    val cutoutRadius = density.run { radiusDp.toPx() }
    val cornerRadius = density.run { cornerRadiusDp.toPx() }

    val shape = remember {
        GenericShape { size: Size, _: LayoutDirection ->
            this.roundedRectanglePath(
                size = size,
                cornerRadius = cornerRadius,
                fabRadius = cutoutRadius * 2
            )
        }
    }

    Spacer(modifier = Modifier.height(diameter))

    Box(contentAlignment = Alignment.TopCenter) {
        FloatingActionButton(
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier
                .offset(y = -diameter / 5)
                .size(diameter)
                .drawBehind {
                    drawCircle(
                        color.copy(.5f),
                        radius = 1.3f * size.width / 2
                    )

                    drawCircle(
                        color.copy(.3f),
                        radius = 1.5f * size.width / 2
                    )
                }
                .align(Alignment.TopCenter),
            onClick = { onClick() }
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White, CircleShape)
                    .clip(CircleShape),
                painter = painterResource(id = image),
                contentDescription = "",
                contentScale = ContentScale.Crop,
            )
        }

        ElevatedCard(
            modifier = modifier,
            shape = shape,
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation)
        ) {
            Column {
                Spacer(modifier = Modifier.height(diameter))

                content()
            }
        }
    }
}