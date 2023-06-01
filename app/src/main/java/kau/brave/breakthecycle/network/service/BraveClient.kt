package kau.brave.breakthecycle.network.service

import kau.brave.breakthecycle.data.request.*
import kau.brave.breakthecycle.data.response.BraveResponse
import kau.brave.breakthecycle.data.response.MensturationInfoResponse
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query
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

    suspend fun register(registerRequest: RegisterRequest) =
        authService.register(registerRequest = registerRequest)

    suspend fun onboard(usePersonId: Int, onBoardRequest: OnBoardRequest) =
        authService.onBoard(usePersonId = usePersonId, onBoardRequest = onBoardRequest)

    suspend fun getMenstruation(
        usePersonId: Int,
        startDate: String,
        endDate: String
    ) = authService.getMenstruation(usePersonId, startDate, endDate)


}