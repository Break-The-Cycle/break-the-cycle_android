package kau.brave.breakthecycle.domain.repository

import androidx.datastore.preferences.core.Preferences
import kau.brave.breakthecycle.data.request.LoginRequest
import kau.brave.breakthecycle.data.response.JwtResponse
import kau.brave.breakthecycle.utils.ApiWrapper
import kotlinx.coroutines.flow.Flow

interface AuthRepository {


    fun login(loginRequest: LoginRequest): Flow<ApiWrapper<JwtResponse>>

    suspend fun setToken(type: Preferences.Key<String>, value: String)

    fun getToken(type: Preferences.Key<String>): Flow<String>
    fun validateAccessToken(): Flow<ApiWrapper<String>>
    fun refreshToken(refreshToken: String): Flow<ApiWrapper<JwtResponse>>
}