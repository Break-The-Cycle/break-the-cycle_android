package kau.brave.breakthecycle.data.domain

import kau.brave.breakthecycle.domain.domain.SHA256Encoder
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Inject


class SHA256EncoderImpl @Inject constructor(
) : SHA256Encoder {

    override fun encode(text: String): String {
        return try {
            val md = MessageDigest.getInstance("SHA-256")
            md.update(text.toByteArray(charset("UTF-8")))
            val numRepresentation = BigInteger(1, md.digest())
            val hashedString = StringBuilder(numRepresentation.toString(16))
            while (hashedString.length < 32) {
                hashedString.insert(0, "0")
            }
            hashedString.toString()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}