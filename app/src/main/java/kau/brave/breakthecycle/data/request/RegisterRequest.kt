package kau.brave.breakthecycle.data.request

import com.squareup.moshi.Json

data class RegisterRequest(
    val loginId: String,
    @Json(name = "name")
    val name: String = "-1",
    val password: String,
    @Json(name = "password2")
    val checkPassword: String,
    val phoneNumber: String
)