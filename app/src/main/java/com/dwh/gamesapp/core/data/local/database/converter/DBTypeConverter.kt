package com.dwh.gamesapp.core.data.local.database.converter;

import android.util.Base64
import androidx.room.TypeConverter
import com.dwh.gamesapp.core.domain.model.EncryptedData
import com.google.gson.Gson

class ByteArrayConverter {
    @TypeConverter
    fun fromByteArray(byteArray: ByteArray): String {
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    @TypeConverter
    fun toByteArray(encodedString: String): ByteArray {
        return Base64.decode(encodedString, Base64.DEFAULT)
    }
}

class EncryptedDataConverter {

    private val gson = Gson()
    private val byteArrayConverter = ByteArrayConverter()

    @TypeConverter
    fun fromEncryptedData(encryptedData: EncryptedData?): String? {
        if (encryptedData == null) return null
        val cipherText = byteArrayConverter.fromByteArray(encryptedData.ciphertext)
        val initializationVector = byteArrayConverter.fromByteArray(encryptedData.initializationVector)
        return gson.toJson(mapOf("cipherText" to cipherText, "initializationVector" to initializationVector))
    }

    @TypeConverter
    fun toEncryptedData(data: String?): EncryptedData? {
        if (data == null) return null
        val map = gson.fromJson(data, Map::class.java)
        val cipherText = byteArrayConverter.toByteArray(map["cipherText"] as String)
        val initializationVector = byteArrayConverter.toByteArray(map["initializationVector"] as String)
        return EncryptedData(cipherText, initializationVector)
    }
}