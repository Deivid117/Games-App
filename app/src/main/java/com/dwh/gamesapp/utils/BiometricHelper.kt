package com.dwh.gamesapp.utils

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.dwh.gamesapp.core.data.repository.CryptoManager
import com.dwh.gamesapp.core.domain.model.EncryptedData
import com.dwh.gamesapp.core.presentation.utils.Constants.ENCRYPTED_FILE_NAME
import com.dwh.gamesapp.core.presentation.utils.Constants.PREFERENCES_DATA_STORE
import com.dwh.gamesapp.core.presentation.utils.Constants.SECRET_KEY
import java.util.UUID

object BiometricHelper {

    // Check if biometric authentication is available on the device
    fun isBiometricAvailable(context: Context): Boolean {
        val biometricManager = BiometricManager.from(context)
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> {
                Log.e("BIOMETRICS", "Biometric authentication not available")
                false
            }
        }
    }

    // Retrieve a BiometricPrompt instance with a predefined callback
    private fun getBiometricPrompt(
        context: Context,
        onAuthSucceed: (BiometricPrompt.AuthenticationResult) -> Unit
    ): BiometricPrompt {
        val biometricPrompt =
            BiometricPrompt(
                context as FragmentActivity,
                ContextCompat.getMainExecutor(context),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult
                    ) {
                        Log.e("BIOMETRIC", "Authentication Succeeded: ${result.cryptoObject}")
                        onAuthSucceed(result)
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        Log.e("BIOMETRIC", "onAuthenticationError")
                    }

                    override fun onAuthenticationFailed() {
                        Log.e("BIOMETRIC", "onAuthenticationFailed")
                    }
                }
            )
        return biometricPrompt
    }

    // Create BiometricPrompt.PromptInfo with customized display text
    private fun getPromptInfo(context: Context): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("Auntenticación Biométric")
            .setSubtitle("Inicia sesión con tu huella")
            .setDescription("Por favor, autentiquese con su huella digital")
            .setNegativeButtonText("CANCEL")
            .setConfirmationRequired(false)
            .build()
    }

    // Register user biometrics by encrypting a randomly generated token
    fun registerUserBiometrics(
        context: Context,
        onSuccess: (authResult: BiometricPrompt.AuthenticationResult, encryptedToken: EncryptedData) -> Unit = { _, _ -> }
    ) {
        val cryptoManager = CryptoManager()
        val cipher = cryptoManager.initEncryptionCipher(SECRET_KEY)
        val biometricPrompt = getBiometricPrompt(context = context as FragmentActivity) { authResult ->
            authResult.cryptoObject?.cipher?.let { cipher ->
                // Dummy token for now(in production app, generate a unique and genuine token
                // for each user registration or consider using token received from authentication server)
                val token = UUID.randomUUID().toString()
                val encryptedToken = cryptoManager.encrypt(token, cipher)

                // Save encrypted token to shared preferences
                cryptoManager.saveFingerPrintId(
                    encryptedToken,
                    context,
                    ENCRYPTED_FILE_NAME,
                    Context.MODE_PRIVATE,
                    PREFERENCES_DATA_STORE
                )
                // Execute custom action on successful registration
                onSuccess(authResult, encryptedToken)
            }
        }
        biometricPrompt.authenticate(
            getPromptInfo(context),
            BiometricPrompt.CryptoObject(cipher)
        )
    }

    // Authenticate user using biometrics by decrypting stored token
    fun authenticateUser(
        context: FragmentActivity,
        onSuccess: (plainText: String, encryptedData: EncryptedData) -> Unit
    ) {
        val cryptoManager = CryptoManager()

        // Retrieve encrypted token from shared preferences
        val encryptedData = cryptoManager.getFingerPrint(
            context,
            ENCRYPTED_FILE_NAME,
            Context.MODE_PRIVATE,
            PREFERENCES_DATA_STORE
        )

        encryptedData?.let { data ->
            val cipher = cryptoManager.initDecryptionCipher(SECRET_KEY, data.initializationVector)
            val biometricPrompt = getBiometricPrompt(context) { authResult ->
                authResult.cryptoObject?.cipher?.let { cipher ->
                    val plainText = cryptoManager.decrypt(data.ciphertext, cipher)
                    // Execute custom action on successful authentication
                    onSuccess(plainText, encryptedData)
                }
            }
            val promptInfo = getPromptInfo(context)
            biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
        }
    }
}


