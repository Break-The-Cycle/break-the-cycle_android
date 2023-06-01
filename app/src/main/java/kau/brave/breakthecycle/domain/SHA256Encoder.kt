package kau.brave.breakthecycle.domain

interface SHA256Encoder {
    fun encode(text: String): String
}