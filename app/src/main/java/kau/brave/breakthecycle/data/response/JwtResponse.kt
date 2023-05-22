package kau.brave.breakthecycle.data.response

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String
)