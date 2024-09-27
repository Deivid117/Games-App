package com.dwh.gamesapp.core.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.theme.Dogica
import com.dwh.gamesapp.core.presentation.theme.primary
import com.dwh.gamesapp.core.presentation.theme.primary_gradient
import com.dwh.gamesapp.core.presentation.theme.red_error
import com.dwh.gamesapp.core.presentation.theme.secondary
import com.dwh.gamesapp.core.presentation.theme.secondary_gradient
import com.dwh.gamesapp.core.presentation.theme.tertiary_cursor
import com.dwh.gamesapp.core.presentation.ui.UiText
import com.dwh.gamesapp.core.presentation.utils.modifier.clearFocusOnKeyboardDismiss
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    value: String,
    onValueChange: (String) -> Unit,
    onClickLabelIcon: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit) = {},
    labelIcon: Int? = null,
    isPasswordTextField: Boolean = false,
    enabled: Boolean = true,
    errorValue: UiText? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var hidePassword by remember { mutableStateOf(true) }
    var isFocused by remember { mutableStateOf(false) }
    val backgroundColor = Color.Transparent
    val borderColor =
        if (errorValue != null) red_error
        else if (isFocused) primary else MaterialTheme.colorScheme.outlineVariant
    val visibilityIcon =
        ImageVector.vectorResource(id = if (hidePassword) R.drawable.ic_visibility else R.drawable.ic_visibility_off)
    val brushGradientColor = Brush.linearGradient(
        0.0f to secondary_gradient,
        500.0f to primary_gradient,
        start = Offset.Zero,
        end = Offset.Infinite
    )
    val bringIntoViewRequester = BringIntoViewRequester()
    val coroutineScope = rememberCoroutineScope()
    val animatedPadding by animateDpAsState(
        targetValue = if (isFocused) 10.dp else 0.dp,
        animationSpec = tween(durationMillis = 300),
        label = ""
    )

    Box(
        modifier = Modifier
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusEvent { focusState ->
                if (focusState.isFocused || focusState.hasFocus || errorValue != null) {
                    coroutineScope.launch {
                        delay(250)
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            }
            .imePadding()
            .padding(bottom = animatedPadding)
    ) {
        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(text = label, style = MaterialTheme.typography.bodySmall.copy(fontFamily = Dogica))

                if (labelIcon != null) {
                    Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .clickable { onClickLabelIcon() },
                        imageVector = ImageVector.vectorResource(id = labelIcon),
                        contentDescription = "information icon"
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = borderColor, shape = CircleShape)
                    .padding(2.dp)
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = backgroundColor)
                        .clearFocusOnKeyboardDismiss()
                        .onFocusChanged { isFocused = it.isFocused },
                    value = value,
                    onValueChange = { onValueChange(it) },
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
                    cursorBrush = SolidColor(tertiary_cursor),
                    singleLine = true,
                    enabled = enabled,
                    visualTransformation = if (isPasswordTextField && hidePassword) PasswordVisualTransformation() else visualTransformation,
                    keyboardActions = keyboardActions,
                    keyboardOptions = keyboardOptions,
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min)
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(brush = brushGradientColor, shape = CircleShape)
                                    .padding(3.dp)
                            ) {
                                leadingIcon()
                            }

                            Box(modifier = Modifier.weight(1f)) {
                                if (value.isBlank() && !isFocused) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        text = placeholder,
                                        color = MaterialTheme.colorScheme.outline,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                                innerTextField()
                            }

                            if (isPasswordTextField) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(brush = brushGradientColor, shape = CircleShape)
                                        .clickable { hidePassword = !hidePassword }
                                        .padding(3.dp)
                                ) {
                                    Icon(
                                        imageVector = visibilityIcon,
                                        contentDescription = "visibility icon",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                )
            }

            if (errorValue != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = errorValue.asString(),
                        color = red_error,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameSearchTextField(
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isExpanded: Boolean = false,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit) = {},
    trailingIcon: @Composable (() -> Unit) = {},
    onClickLeadingIcon: () -> Unit = {},
    onClickTrailingIcon: () -> Unit = {},
    enabled: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var isFocused by remember { mutableStateOf(false) }
    val padding by animateDpAsState(targetValue = if (isExpanded) 8.dp else 0.dp, label = "")
    val textFieldBackgroundColor by animateColorAsState(
        targetValue = if (isExpanded) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent, label = ""
    )
    val iconBackgroundColor by animateColorAsState(
        targetValue = if (isExpanded) secondary else Color.White, label = ""
    )
    val bringIntoViewRequester = BringIntoViewRequester()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusEvent { focusState ->
                if (focusState.isFocused || focusState.hasFocus) {
                    coroutineScope.launch {
                        delay(250)
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            }
    ) {
        BasicTextField(
            modifier = modifier
                .fillMaxWidth()
                .background(color = textFieldBackgroundColor, shape = CircleShape)
                .clearFocusOnKeyboardDismiss()
                .onFocusChanged { isFocused = it.isFocused },
            value = value,
            onValueChange = { onValueChange(it) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
            cursorBrush = SolidColor(tertiary_cursor),
            singleLine = true,
            enabled = enabled,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(padding),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = iconBackgroundColor, shape = CircleShape)
                            .clickable { onClickLeadingIcon() }
                            .padding(3.dp)
                    ) { leadingIcon() }

                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isBlank() && !isFocused) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = placeholder,
                                color = MaterialTheme.colorScheme.outline,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        innerTextField()
                    }

                    AnimatedVisibility(
                        visible = value.isNotEmpty(),
                        enter = fadeIn(animationSpec = tween(300)),
                        exit = fadeOut(animationSpec = tween(300))
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { onClickTrailingIcon() }
                                .padding(3.dp)
                        ) { trailingIcon() }
                    }
                }
            }
        )
    }
}