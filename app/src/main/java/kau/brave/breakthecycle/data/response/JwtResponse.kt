package kau.brave.breakthecycle.data.response

data class JwtResponse(
    val userId: Int,
    val accessToken: String,
    val refreshToken: String
)