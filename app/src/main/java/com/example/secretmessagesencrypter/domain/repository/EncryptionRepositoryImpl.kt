package com.example.secretmessagesencrypter.domain.repository

import android.util.Base64
import com.example.secretmessagesencrypter.utils.Constants
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class EncryptionRepositoryImpl: EncryptionRepository {

    // Define a fixed 16-byte key (e.g., "MyFixedAESKey123")
//    private val fixedKey = "MyFixedAESKey123".toByteArray()

    override fun encrypt(text: String, key: ByteArray): String {
        val cipher = Cipher.getInstance(Constants.ACS)
        cipher.init(Cipher.ENCRYPT_MODE, getFixedKeySpec(key))
        val encryptedBytes = cipher.doFinal(text.toByteArray())
        return try {
            Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        }catch (e: IllegalArgumentException){
            "Error: ${e.message}"
        }
    }

    override fun decrypt(text: String, key: ByteArray): String {
        return try {
            val cipher = Cipher.getInstance(Constants.ACS)
            cipher.init(Cipher.DECRYPT_MODE, getFixedKeySpec(key))
            val decodedBytes = Base64.decode(text, Base64.DEFAULT)
            val decryptedBytes = cipher.doFinal(decodedBytes)
            String(decryptedBytes)
        }catch (e: IllegalArgumentException){
            "Error:  ${e.message}"
        }
    }

    override fun generateKey(fixedKey: ByteArray): SecretKey {
        // For this implementation, we return the fixed key directly.
        return SecretKeySpec(fixedKey, Constants.ACS)
    }

    private fun getFixedKeySpec(fixedKey: ByteArray): SecretKeySpec {
        return SecretKeySpec(fixedKey, Constants.ACS)
    }
}