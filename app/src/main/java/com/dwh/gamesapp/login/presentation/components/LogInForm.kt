package com.dwh.gamesapp.login.presentation.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.GameTextField
import com.dwh.gamesapp.core.presentation.ui.UiText

@Composable
fun LogInForm(
    email: String,
    password: String,
    emailError: UiText?,
    passwordError: UiText?,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    GameTextField(
        value = email,
        onValueChange = { onEmailChanged(it) },
        label = "Email",
        placeholder = "Enter Email",
        errorValue = emailError,
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_email),
                contentDescription = "person icon",
                tint = Color.White
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
    )

    GameTextField(
        value = password,
        onValueChange = { onPasswordChanged(it) },
        label = "Password",
        placeholder = "Enter Password",
        errorValue = passwordError,
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_lock),
                contentDescription = "password icon",
                tint = Color.White
            )
        },
        isPasswordTextField = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() })
    )
}