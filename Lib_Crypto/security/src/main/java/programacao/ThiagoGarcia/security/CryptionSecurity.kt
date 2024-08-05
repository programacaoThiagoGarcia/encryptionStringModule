package programacao.ThiagoGarcia.security

import android.util.Base64
import java.net.URLEncoder
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.streams.asSequence

fun encryptionString(textToEncrypt: String, keyTxt: String): ReturnCryptoString {

    val encKey = URLEncoder.encode(keyTxt, "UTF-8")
    val key = SecretKeySpec(encKey.toByteArray(), "AES")

    val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
    val iv = createKey()
    val ivSpec = IvParameterSpec(iv.toByteArray())
    cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec)

    val encryptedBytes = cipher.doFinal(textToEncrypt.toByteArray())
    val encodeBytes: ByteArray = Base64.encode(encryptedBytes, Base64.DEFAULT)

    val encryptedTxt = String(encodeBytes, Charset.forName("UTF-8"))
    return Pair(encryptedTxt, String(ivSpec.iv, Charset.forName("UTF-8")))
}

typealias ReturnCryptoString = Pair<String, String>
typealias ReturnCryptoStrings = Pair<Pair<String, String>, String>


private fun createKey(): String {
    val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return java.util.Random().ints(16, 0, source.length)
        .asSequence()
        .map(source::get)
        .joinToString("")
}

fun encryptionStrings(
    firstString: String,
    secondString: String,
    keyTxt: String
): ReturnCryptoStrings {
    val encKey = URLEncoder.encode(keyTxt, "UTF-8")
    val key = SecretKeySpec(encKey.toByteArray(), "AES")

    val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
    val iv = createKey()
    val ivSpec = IvParameterSpec(iv.toByteArray())
    cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec)

    val encryptedBytesFirstString = cipher.doFinal(firstString.toByteArray())
    val encodeBytesFirstString: ByteArray = Base64.encode(encryptedBytesFirstString, Base64.DEFAULT)

    val encryptedBytesSecondString = cipher.doFinal(secondString.toByteArray())
    val encodeBytesSecondString: ByteArray =
        Base64.encode(encryptedBytesSecondString, Base64.DEFAULT)

    val encryptedFirstTxt = String(encodeBytesFirstString, Charset.forName("UTF-8"))
    val encryptedSecondTxt = String(encodeBytesSecondString, Charset.forName("UTF-8"))

    return Pair(
        Pair(encryptedFirstTxt, encryptedSecondTxt),
        String(ivSpec.iv, Charset.forName("UTF-8"))
    )
}
