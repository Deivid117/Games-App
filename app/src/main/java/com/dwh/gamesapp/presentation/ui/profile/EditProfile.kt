package com.dwh.gamesapp.presentation.ui.profile

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dwh.gamesapp.R
import com.dwh.gamesapp.domain.model.user.User
import com.dwh.gamesapp.presentation.composables.*
import com.dwh.gamesapp.presentation.view_model.edit_profile.EditProfileViewModel

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.getValues()
    }

    CustomScaffold(
        navController = navController,
        showBottomBar = false,
        showTopBar = true,
        showTopBarColor = true,
        title = "Edit Profile",
        onBackClick = { navController.popBackStack() }
    ) {
        BackgroundGradient()
        EditProfileContent(viewModel, navController)
    }
}

@Composable
fun EditProfileContent(
    viewModel: EditProfileViewModel,
    navController: NavController
) {
    val userNameData by viewModel.userData.collectAsStateWithLifecycle()
    var userName by remember { mutableStateOf(userNameData.userName) }
    LaunchedEffect(userNameData.userName) {
        userName = userNameData.userName
    }
    var password by remember { mutableStateOf("") }
    var passwordConfirmation by remember { mutableStateOf("") }

    var showSuccessDialog by remember { mutableStateOf(false) }

    ShowEditProfileSuccessDialog(showSuccessDialog) {
        showSuccessDialog = false
        navController.popBackStack()
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
            .verticalScroll(rememberScrollState())) {
        CustomArcShape(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .padding(bottom = 30.dp),
            ) {
                EditProfileForm(
                    userName,
                    password,
                    passwordConfirmation,
                    onNameChanged = { userName = it },
                    onPasswordChanged = { password = it },
                    onPasswordConfirmationChanged = { passwordConfirmation = it }
                )
            }
            BottomEditProfileButton(){
                viewModel.updateUser(User(
                    name = userName,
                    password = password,
                    passwordConfirmation = passwordConfirmation
                )) { success ->
                    if(success) {
                        password = ""
                        passwordConfirmation = ""
                        showSuccessDialog = true
                    }
                } }
        }
    }
}

@Composable
private fun EditProfileForm(
    userName: String,
    password: String,
    passwordConfirmation: String,
    onNameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onPasswordConfirmationChanged: (String) -> Unit
) {
    Text(text = "User Name", style = MaterialTheme.typography.bodyMedium)
    CustomTextField(
        modifier = Modifier.fillMaxWidth(),
        value = userName,
        onValueChange = { onNameChanged(it) },
        placeholder = "Escribe tu nombre de usuario",
        label = "User Name"
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(text = "Password", style = MaterialTheme.typography.bodyMedium)
    CustomTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = { onPasswordChanged(it) },
        placeholder = "Escribe tu contraseña",
        label = "Password",
        isPasswordTextField = true
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(text = "Password Confirmation", style = MaterialTheme.typography.bodyMedium)
    CustomTextField(
        modifier = Modifier.fillMaxWidth(),
        value = passwordConfirmation,
        onValueChange = { onPasswordConfirmationChanged(it) },
        placeholder = "Confirma tu contraseña",
        label = "Password Confirmation",
        isPasswordTextField = true
    )
}

@Composable
private fun BottomEditProfileButton(
    onClickAction: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(15.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        CustomButton(
            Modifier.fillMaxWidth(),
            onClick = { onClickAction() },
            nameButton = "SAVE"
        )
    }
}

@Composable
private fun ShowEditProfileSuccessDialog(
    showSuccessDialog: Boolean,
    onDissmiss: () -> Unit
) {
    if(showSuccessDialog) {
        CustomDialog(
            animation = R.raw.heart_animation,
            onDissmiss = { onDissmiss() },
            title = "Se ha modificado tu perfil",
        ) {}
    }
}
