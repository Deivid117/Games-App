package com.dwh.gamesapp.edit_profile.presentation.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.GameTextField
import com.dwh.gamesapp.core.presentation.ui.UiText

@Composable
fun EditProfileForm(
    name: String,
    password: String,
    passwordConfirmation: String,
    nameError: UiText?,
    passwordError: UiText?,
    passwordConfirmationError: UiText?,
    onNameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onPasswordConfirmationChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    GameTextField(
        value = name,
        onValueChange = { onNameChanged(it) },
        label = stringResource(id = R.string.edit_profile_name_textfield_label),
        placeholder = stringResource(id = R.string.edit_profile_name_textfield_placeholder),
        errorValue = nameError,
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_person),
                contentDescription = "person icon",
                tint = Color.White
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
    )

    GameTextField(
        value = password,
        onValueChange = { onPasswordChanged(it) },
        label = stringResource(id = R.string.edit_profile_password_textfield_label),
        placeholder = stringResource(id = R.string.edit_profile_password_textfield_placeholder),
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
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) })
    )

    GameTextField(
        value = passwordConfirmation,
        onValueChange = { onPasswordConfirmationChanged(it) },
        label = stringResource(id = R.string.edit_profile_confirm_password_textfield_label),
        placeholder = stringResource(id = R.string.edit_profile_confirm_password_textfield_placeholder),
        errorValue = passwordConfirmationError,
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