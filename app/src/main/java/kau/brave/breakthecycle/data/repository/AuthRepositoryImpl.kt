package kau.brave.breakthecycle.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kau.brave.breakthecycle.data.request.*
import kau.brave.breakthecycle.data.response.JwtResponse
import kau.brave.breakthecycle.data.response.RegisterIdResponse
import kau.brave.breakthecycle.domain.repository.AuthRepository
import kau.brave.breakthecycle.network.model.apiFlow
import kau.brave.breakthecycle.network.service.BraveClient
import kau.brave.breakthecycle.utils.ApiWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val braveClient: BraveClient,
    private val preferenceDataStore: DataStore<Preferences>,
) : AuthRepository {

    override fun login(loginRequest: LoginRequest): Flow<ApiWrapper<JwtResponse>> = apiFlow {
        braveClient.login(loginRequest = loginRequest)
    }

    override fun validateAccessToken(): Flow<ApiWrapper<String>> = apiFlow {
        braveClient.validateAccessToken()
    }

    override fun refreshToken(refreshToken: String): Flow<ApiWrapper<JwtResponse>> = apiFlow {
        braveClient.refreshToken(refreshToken = refreshToken)
    }

    override fun checkDupNickname(loginId: String): Flow<ApiWrapper<String>> = apiFlow {
        braveClient.dupIdCheck(loginId = loginId)
    }

    override fun sendCertificationCode(phoneNumber: PhoneNumber): Flow<ApiWrapper<String>> =
        apiFlow {
            braveClient.sendCertificationCode(phoneNumber = phoneNumber)
        }

    override fun confirmCetificationCode(phoneAndCertificationNumber: PhoneAndCertificationNumber): Flow<ApiWrapper<String>> =
        apiFlow {
            braveClient.confirmCetificationCode(phoneAndCertificationNumber)
        }

    override fun signIn(registerRequest: RegisterRequest): Flow<ApiWrapper<RegisterIdResponse>> =
        apiFlow {
            braveClient.register(registerRequest = registerRequest)
        }

    override fun onboard(
        usePersonId: Int,
        onBoardRequest: OnBoardRequest
    ): Flow<ApiWrapper<String>> = apiFlow {
        braveClient.onboard(usePersonId = usePersonId, onBoardRequest = onBoardRequest)
    }


    override suspend fun setToken(type: Preferences.Key<String>, value: String) {
        preferenceDataStore.edit { settings ->
            settings[type] = value
        }
    }

    override fun getToken(type: Preferences.Key<String>): Flow<String> {
        return preferenceDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { prefs ->
                prefs[type] ?: ""
            }
    }
}