package com.dwh.gamesapp.core.domain.repository

import android.content.Context
import com.dwh.gamesapp.core.domain.model.EncryptedData
import javax.crypto.Cipher

interface CryptographyManagerRepository {
    fun initEncryptionCipher(keyName: String): Cipher

    fun initDecryptionCipher(keyName: String, initializationVector: ByteArray): Cipher

    fun encrypt(plaintext: String, cipher: Cipher): EncryptedData

    fun decrypt(ciphertext: ByteArray, cipher: Cipher): String

    fun saveFingerPrintId(
        encryptedData: EncryptedData,
        context: Context,
        filename: String,
        mode: Int,
        prefKey: String
    )

    fun getFingerPrint(
        context: Context,
        filename: String,
        mode: Int,
        prefKey: String
    ): EncryptedData?
}