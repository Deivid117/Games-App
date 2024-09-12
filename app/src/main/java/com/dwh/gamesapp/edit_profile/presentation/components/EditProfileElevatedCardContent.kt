package com.dwh.gamesapp.edit_profile.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.GameFilledButton
import com.dwh.gamesapp.core.presentation.ui.UiText

@Composable
fun EditProfileElevatedCardContent(
    userProfileAvatar: Int,
    name: String,
    password: String,
    confirmPassword: String,
    nameError: UiText?,
    passwordError: UiText?,
    passwordConfirmationError: UiText?,
    formHasErrors: Boolean,
    onNameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onPasswordConfirmationChanged: (String) -> Unit,
    onClickUserAvatar: () -> Unit,
    onClickSaveButton: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(top = 64.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EditProfileElevatedArcCard(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize(),
            image = userProfileAvatar,
            onClick = onClickUserAvatar
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(top = 15.dp, bottom = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                EditProfileForm(
                    name = name,
                    password = password,
                    passwordConfirmation = confirmPassword,
                    nameError = nameError,
                    passwordError = passwordError,
                    passwordConfirmationError = passwordConfirmationError,
                    onNameChanged = { onNameChanged(it) },
                    onPasswordChanged = { onPasswordChanged(it) },
                    onPasswordConfirmationChanged = { onPasswordConfirmationChanged(it) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                GameFilledButton(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    nameButton = stringResource(id = R.string.edit_profile_btn_save),
                    anErrorOccurred = formHasErrors,
                    onClick = onClickSaveButton
                )
            }
        }
    }
}