package kau.brave.breakthecycle.data.request

import com.squareup.moshi.Json

data class RegisterRequest(
    val loginId: String,
    val password2: String,
    val password: String,
    val phoneNumber: String
)