package kau.brave.breakthecycle.network.service

import kau.brave.breakthecycle.data.request.LoginRequest
import kau.brave.breakthecycle.data.request.PhoneNumberRequest
import kau.brave.breakthecycle.data.request.PhoneNumberWithCertificationNumberRequest
import kau.brave.breakthecycle.data.request.RegisterRequest
import kau.brave.breakthecycle.data.response.BraveResponse
import kau.brave.breakthecycle.data.response.JwtResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


interface AuthService {

    @GET("/v1/auth/refresh")
    suspend fun refreshToken(
        @Header("Refresh-Token") refreshToken: String
    ): Response<BraveResponse<JwtResponse>>

    @GET("/v1/auth/duplicate-check/{loginId}")
    suspend fun duplicateIdCheck(
        @Query("loginId") loginId: String
    ): Response<BraveResponse<String>>

    @POST("/v1/auth/sms-certification/send")
    suspend fun sendSmsCertification(
        @Body phoneNumberRequest: PhoneNumberRequest
    ): Response<BraveResponse<String>>

    @POST("/v1/auth/sms-certification/confirm")
    suspend fun confirmSmsCertification(
        @Body phoneNumberWithCertificationNumberRequest: PhoneNumberWithCertificationNumberRequest
    ): Response<BraveResponse<String>>

    @POST("/v1/auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<BraveResponse<String>>

    @POST("/v1/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<BraveResponse<JwtResponse>>

    @POST("/v1/auth/user")
    suspend fun validateAccessToken(): Response<BraveResponse<String>>

}