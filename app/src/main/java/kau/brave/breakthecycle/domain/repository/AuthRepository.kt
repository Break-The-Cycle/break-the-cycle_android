package kau.brave.breakthecycle.domain.repository

import androidx.datastore.preferences.core.Preferences
import kau.brave.breakthecycle.data.request.*
import kau.brave.breakthecycle.data.response.JwtResponse
import kau.brave.breakthecycle.utils.ApiWrapper
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun login(loginRequest: LoginRequest): Flow<ApiWrapper<JwtResponse>>

    suspend fun setToken(type: Preferences.Key<String>, value: String)

    fun getToken(type: Preferences.Key<String>): Flow<String>
    fun validateAccessToken(): Flow<ApiWrapper<String>>
    fun refreshToken(refreshToken: String): Flow<ApiWrapper<JwtResponse>>

    fun checkDupNickname(nickname: String): Flow<ApiWrapper<String>>

    fun sendCertificationCode(phoneNumber: PhoneNumber): Flow<ApiWrapper<String>>

    fun confirmCetificationCode(phoneAndCertificationNumber: PhoneAndCertificationNumber): Flow<ApiWrapper<String>>

    fun signIn(registerRequest: RegisterRequest): Flow<ApiWrapper<String>>
    fun onboard(usePersonId: Int, onBoardRequest: OnBoardRequest): Flow<ApiWrapper<String>>
}