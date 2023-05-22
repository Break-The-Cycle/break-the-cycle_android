package kau.brave.breakthecycle.network.service

import kau.brave.breakthecycle.data.request.LoginRequest
import kau.brave.breakthecycle.data.request.PhoneAndCertificationNumber
import kau.brave.breakthecycle.data.request.PhoneNumber
import javax.inject.Inject

class BraveClient @Inject constructor(
    private val authService: AuthService
) {

    /** Auth */
    suspend fun login(loginRequest: LoginRequest) = authService.login(loginRequest = loginRequest)
    suspend fun validateAccessToken() = authService.validateAccessToken()
    suspend fun refreshToken(refreshToken: String) =
        authService.refreshToken(refreshToken = refreshToken)

    suspend fun dupIdCheck(loginId: String) =
        authService.dupIdCheck(loginId = loginId)

    suspend fun sendCertificationCode(phoneNumber: PhoneNumber) =
        authService.sendSmsCertificationCode(phoneNumber = phoneNumber)

    suspend fun confirmCetificationCode(phoneAndCertificationNumber: PhoneAndCertificationNumber) =
        authService.confirmCertificationCode(phoneAndCertificationNumber)

}