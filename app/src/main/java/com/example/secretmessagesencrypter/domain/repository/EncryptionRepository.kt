package com.example.secretmessagesencrypter.domain.repository

import javax.crypto.SecretKey
import java.security.Key


interface EncryptionRepository {

    fun encrypt(text: String, key: ByteArray): String
    fun decrypt(text: String, key: ByteArray): String
    fun generateKey(fixedKey: ByteArray): SecretKey

}