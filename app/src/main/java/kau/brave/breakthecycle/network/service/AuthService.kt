package kau.brave.breakthecycle.network.service

import kau.brave.breakthecycle.data.request.*
import kau.brave.breakthecycle.data.response.BraveResponse
import kau.brave.breakthecycle.data.response.JwtResponse
import kau.brave.breakthecycle.data.response.MenstruationInfoResponse
import kau.brave.breakthecycle.data.response.RegisterIdResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface AuthService {

    @GET("v1/auth/refresh")
    suspend fun refreshToken(
        @Header("Refresh-Token") refreshToken: String
    ): Response<BraveResponse<JwtResponse>>

    @GET("v1/auth/duplicate-check/{loginId}")
    suspend fun dupIdCheck(
        @Query("loginId") loginId: String
    ): Response<BraveResponse<String>>

    @POST("v1/auth/sms-certification/send")
    suspend fun sendSmsCertificationCode(
        @Body phoneNumber: PhoneNumber
    ): Response<BraveResponse<String>>

    @POST("v1/auth/sms-certification/confirm")
    suspend fun confirmCertificationCode(
        @Body phoneAndCertificationNumber: PhoneAndCertificationNumber
    ): Response<BraveResponse<String>>

    @POST("v1/auth/register/use-person")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<BraveResponse<RegisterIdResponse>>

    @POST("v1/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<BraveResponse<JwtResponse>>

    @POST("v1/auth/user")
    suspend fun validateAccessToken(): Response<BraveResponse<String>>

    /** 온보딩 초기 생리주기 설정 */
    @POST("v1/use-persons/{usePersonId}/on-board")
    suspend fun onBoard(
        @Path("usePersonId") usePersonId: Int,
        @Body onBoardRequest: OnBoardRequest
    ): Response<BraveResponse<String>>

    /** 생리 기간 재 설정 */
    @PUT("v1/use-persons/{usePersonId}/menstruation-period")
    suspend fun updateMenstruationPeriod(
        @Path("usePersonId") usePersonId: Int,
        @Query("period") period: Int
    ): Response<BraveResponse<String>>

    /** 생리 주기 재 등록 */
    @POST("v1/use-persons/{usePersonId}/menstruation")
    suspend fun updateMenstruation(
        @Path("usePersonId") usePersonId: Int,
        @Body menstruationRequest: MenstruationRequest
    ): Response<BraveResponse<String>>


    /** 생리 기록 가져오기 */
    @GET("v1/use-persons/{usePersonId}/menstruation")
    suspend fun getMenstruation(
        @Path("usePersonId") usePersonId: Int,
        @Query("fromDate") startDate: String,
        @Query("toDate") endDate: String
    ): Response<BraveResponse<MenstruationInfoResponse>>


}