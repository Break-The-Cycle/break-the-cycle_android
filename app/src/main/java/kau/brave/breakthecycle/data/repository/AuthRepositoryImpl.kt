package kau.brave.breakthecycle.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kau.brave.breakthecycle.data.request.LoginRequest
import kau.brave.breakthecycle.data.response.JwtResponse
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