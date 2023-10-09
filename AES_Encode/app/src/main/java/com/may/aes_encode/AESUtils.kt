package com.may.aes_encode

import android.util.Base64
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

private val SECRET_KEY = "Jensen0906xxm423"
private val AES = "AES"
private val CHARSET = Charset.forName("utf-8")
private val CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding"

fun String?.AESEncode(): String {
    if (isNullOrEmpty()) {
        return ""
    }
    val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
    val byteArray = SECRET_KEY.toByteArray(CHARSET)
    val keySpec = SecretKeySpec(byteArray, AES)
    val iv = IvParameterSpec(byteArray)
    return try {
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)
        val encrypted = cipher.doFinal(toByteArray(CHARSET))
        Base64.encodeToString(encrypted, Base64.NO_WRAP)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun String?.AESDecode(): String {
    if (isNullOrEmpty()) {
        return ""
    }
    val encrypted = Base64.decode(toByteArray(CHARSET), Base64.NO_WRAP)
    val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
    val byteArray = SECRET_KEY.toByteArray(CHARSET)
    val keySpec = SecretKeySpec(byteArray, AES)
    val iv = IvParameterSpec(byteArray)
    return try {
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)
        val original = cipher.doFinal(encrypted)
        String(original, CHARSET)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}