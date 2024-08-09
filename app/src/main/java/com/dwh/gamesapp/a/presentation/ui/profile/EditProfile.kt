package com.dwh.gamesapp.a.presentation.ui.profile

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
import com.dwh.gamesapp.a.domain.model.user.User
import com.dwh.gamesapp.a.presentation.composables.Avatars
import com.dwh.gamesapp.a.presentation.composables.AvatarsDialog
import com.dwh.gamesapp.a.presentation.composables.CustomArcShape
import com.dwh.gamesapp.a.presentation.composables.CustomButton
import com.dwh.gamesapp.a.presentation.composables.CustomDialog
import com.dwh.gamesapp.a.presentation.composables.GameScaffold
import com.dwh.gamesapp.a.presentation.composables.CustomTextField
import com.dwh.gamesapp.a.presentation.view_model.edit_profile.EditProfileViewModel

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.getValues()
    }

    GameScaffold(
        navController = navController,
        isBottomBarVisible = false,
        isTopBarVisible = true,
        showTopBarColor = true,
        title = "Edit Profile",
        onBackClick = { navController.popBackStack() }
    ) {
        EditProfileContent(viewModel, navController)
    }
}

@Composable
fun EditProfileContent(
    viewModel: EditProfileViewModel,
    navController: NavController
) {
    val userData by viewModel.userData.collectAsStateWithLifecycle()

    var userName by remember { mutableStateOf("") }
    var userAvatarId by remember { mutableIntStateOf(0) }

    LaunchedEffect(userData) {
        userName = userData.userName
        userAvatarId = userData.imageId
    }



    var password by remember { mutableStateOf("") }
    var passwordConfirmation by remember { mutableStateOf("") }

    val avatars = listOf(
        Avatars.Avatar1,
        Avatars.Avatar2,
        Avatars.Avatar3,
    )

    var avatarImage by remember { mutableStateOf(R.drawable.ic_user_unfilled) }

    for (i in avatars.indices) {
        if(avatars[i].id == userAvatarId.toLong()) {
            avatarImage = avatars[i].image
        }
    }

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showAvatarsDialog by remember { mutableStateOf(false) }

    ShowEditProfileSuccessDialog(showSuccessDialog) {
        showSuccessDialog = false
        navController.popBackStack()
    }

    ShowAvatarDialog(
        showAvatarsDialog,
        onDismiss = {
            showAvatarsDialog = false
        },
        onSelectedAvatar = { avatar, id ->
            avatarImage = avatar
            userAvatarId = id.toInt()
        }
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
            .verticalScroll(rememberScrollState())) {
        CustomArcShape(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            image = avatarImage,
            onShowAvatarDialog = { showAvatarsDialog = true }
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
                viewModel.updateUser(
                    User(
                    name = userName,
                    password = password,
                    passwordConfirmation = passwordConfirmation,
                    image_id = userAvatarId.toLong()
                )
                ) { success ->
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
    Text(
        text = "User Name",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondaryContainer
    )

    CustomTextField(
        modifier = Modifier.fillMaxWidth(),
        value = userName,
        onValueChange = { onNameChanged(it) },
        placeholder = "Escribe tu nombre de usuario",
        label = "User Name"
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = "Password",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondaryContainer
    )

    CustomTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = { onPasswordChanged(it) },
        placeholder = "Escribe tu contraseña",
        label = "Password",
        isPasswordTextField = true
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = "Password Confirmation",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondaryContainer
    )

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

@Composable
private fun ShowAvatarDialog(
    showSuccessDialog: Boolean,
    onDismiss: () -> Unit,
    onSelectedAvatar: (Int, Long) -> Unit
) {
    if(showSuccessDialog) {
        AvatarsDialog(
            onDismiss = { onDismiss() },
            onSelectedAvatar = { image, id ->
                onSelectedAvatar(image, id)
            }
        )
    }
}