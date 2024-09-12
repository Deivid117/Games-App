package com.dwh.gamesapp.login.presentation.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwh.gamesapp.R
import com.dwh.gamesapp.core.presentation.composables.GameFilledButton
import com.dwh.gamesapp.core.presentation.composables.GameTitleGradientText
import com.dwh.gamesapp.core.presentation.theme.primary
import com.dwh.gamesapp.core.presentation.ui.UiText

@Composable
fun LogInElevatedCardContent(
    email: String,
    password: String,
    emailError: UiText?,
    passwordError: UiText?,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onClickRememberUser: (Boolean) -> Unit,
    anErrorOccurred: Boolean,
    onClickLogin: () -> Unit,
    navigateToSignUp: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(15.dp)
            .padding(top = 140.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                GameTitleGradientText(
                    modifier = Modifier.fillMaxWidth(),
                    text = "SIGN IN",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                LogInForm(
                    email = email,
                    password = password,
                    emailError = emailError,
                    passwordError = passwordError,
                    onEmailChanged = onEmailChanged,
                    onPasswordChanged = onPasswordChanged
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RememberMeCheckBox(modifier = Modifier.weight(1f)) { isChecked ->
                        onClickRememberUser(isChecked)
                    }

                    Text(
                        modifier = Modifier.clickable {
                            Toast.makeText(context, "No funca c:", Toast.LENGTH_SHORT).show()
                        },
                        text = "Forgot password",
                        style = MaterialTheme.typography.labelLarge,
                        color = primary
                    )
                }

                GameFilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    nameButton = stringResource(id = R.string.signup_btn_sign_up),
                    anErrorOccurred = anErrorOccurred,
                    onClick = onClickLogin
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Still not connected? ",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.outline
                    )

                    Text(
                        modifier = Modifier.clickable { navigateToSignUp() },
                        text = "Sign Up",
                        style = MaterialTheme.typography.labelLarge,
                        color = primary
                    )
                }
            }
        }
    }
}