package kau.brave.breakthecycle.data.request

data class RegisterRequest(
    val name: String,
    val loginId: String,
    val password: String,
    val password2: String,
    val phoneNumber: String
)