package kau.brave.breakthecycle.domain.domain

interface SHA256Encoder {
    fun encode(text: String): String
}