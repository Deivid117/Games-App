package com.dwh.gamesapp.core.data.repository

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.dwh.gamesapp.core.domain.model.EncryptedData
import com.dwh.gamesapp.core.domain.repository.CryptographyManagerRepository
import com.google.gson.Gson
import java.nio.charset.Charset
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

fun CryptoManager(): CryptographyManagerRepository = CryptographyManagerRepositoryImpl()

class CryptographyManagerRepositoryImpl @Inject constructor()  : CryptographyManagerRepository {

    private val ENCRYPTION_TRANSFORMATION = "AES/GCM/NoPadding"
    private val ANDROID_KEYSTORE = "AndroidKeyStore"
    private val KEY_ALIAS = "MyKeyAlias"

    private val keyStore: KeyStore = KeyStore.getInstance(ANDROID_KEYSTORE)

    init {
        keyStore.load(null)
        if (!keyStore.containsAlias(KEY_ALIAS)) {
            createSecretKey()
        }
    }

    override fun initEncryptionCipher(keyName: String): Cipher {
        val cipher = Cipher.getInstance(ENCRYPTION_TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        return cipher    }

    override fun initDecryptionCipher(keyName: String, initializationVector: ByteArray): Cipher {
        val cipher = Cipher.getInstance(ENCRYPTION_TRANSFORMATION)
        val spec = GCMParameterSpec(128, initializationVector)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec)
        return cipher
    }

    override fun encrypt(plaintext: String, cipher: Cipher): EncryptedData {
        val encryptedBytes = cipher.doFinal(plaintext.toByteArray(Charset.forName("UTF-8")))
        return EncryptedData(encryptedBytes, cipher.iv)
    }

    override fun decrypt(ciphertext: ByteArray, cipher: Cipher): String {
        val decryptedBytes = cipher.doFinal(ciphertext)
        return String(decryptedBytes, Charset.forName("UTF-8"))
    }

    override fun saveFingerPrintId(
        encryptedData: EncryptedData,
        context: Context,
        filename: String,
        mode: Int,
        prefKey: String
    ) {
        val json = Gson().toJson(encryptedData)
        with(context.getSharedPreferences(filename, mode).edit()) {
            putString(prefKey, json)
            apply()
        }
    }

    override fun getFingerPrint(context: Context, filename: String, mode: Int, prefKey: String): EncryptedData? {
        val json = context.getSharedPreferences(filename, mode).getString(prefKey, null)
        return Gson().fromJson(json, EncryptedData::class.java)
    }

    private fun createSecretKey() {
        val keyGenParams = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).apply {
            setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            setUserAuthenticationRequired(true)
        }.build()

        val keyGenerator =
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
        keyGenerator.init(keyGenParams)
        keyGenerator.generateKey()
    }

    private fun getSecretKey(): SecretKey {
        return keyStore.getKey(KEY_ALIAS, null) as SecretKey
    }
}