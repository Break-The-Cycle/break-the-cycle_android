package kau.brave.breakthecycle.network.service

import kau.brave.breakthecycle.data.request.LoginRequest
import javax.inject.Inject

class BraveClient @Inject constructor(
    private val authService: AuthService
) {

    /** Auth */
    suspend fun login(loginRequest: LoginRequest) = authService.login(loginRequest = loginRequest)
    suspend fun validateAccessToken() = authService.validateAccessToken()
    suspend fun refreshToken(refreshToken: String) =
        authService.refreshToken(refreshToken = refreshToken)

}