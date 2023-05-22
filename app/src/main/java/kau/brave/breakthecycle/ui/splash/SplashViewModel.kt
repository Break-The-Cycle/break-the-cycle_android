package kau.brave.breakthecycle.ui.splash

import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kau.brave.breakthecycle.di.DispatcherModule
import kau.brave.breakthecycle.domain.repository.AuthRepository
import kau.brave.breakthecycle.network.ServiceInterceptor
import kau.brave.breakthecycle.utils.Constants.PREF_ACCESS_TOKEN
import kau.brave.breakthecycle.utils.Constants.PREF_REFRESH_TOKEN
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @DispatcherModule.IoDispatcher private val IODispatcher: CoroutineDispatcher,
    @DispatcherModule.MainDispatcher private val MainDispatcher: CoroutineDispatcher
) : ViewModel() {

    fun loginCheck(navigateToMain: () -> Unit, navigateToLogin: () -> Unit) =
        viewModelScope.launch {
            val accessToken = withContext(IODispatcher) {
                authRepository.getToken(PREF_ACCESS_TOKEN).first()
            }
            val refreshToken = withContext(IODispatcher) {
                authRepository.getToken(PREF_REFRESH_TOKEN).first()
            }
            if (accessToken.isNotBlank()) {
                // 엑세스토큰이 빈 값이 아니라면 (로그인이 되어 있다면) 프레시토큰 검증을한다.
                validateRefreshToken(navigateToLogin, navigateToMain, refreshToken)
            } else {
                launch(MainDispatcher) { navigateToLogin() }
            }
        }

    private fun validateRefreshToken(
        navigateToLogin: () -> Unit,
        navigateToMain: () -> Unit,
        refreshToken: String,
    ) = viewModelScope.launch {
        authRepository.refreshToken(refreshToken).collect { apiState ->
            apiState.onSuccess {
                if (it.data == null) {
                    navigateToLogin()
                    return@onSuccess
                }
                val newAccessToken = it.data.accessToken
                val newRrefreshToken = it.data.refreshToken
                Log.i("ACCESS-TOKEN", newAccessToken)
                Log.i("REFRESH-TOKEN", newRrefreshToken)
                setToken(PREF_ACCESS_TOKEN, newAccessToken)
                setToken(PREF_REFRESH_TOKEN, newRrefreshToken)
                ServiceInterceptor.accessToken = newAccessToken
                ServiceInterceptor.refreshToken = newRrefreshToken
                launch(MainDispatcher) { navigateToMain() }
            }
            apiState.onError {
                launch(MainDispatcher) { navigateToLogin() }
            }
        }
    }

    private fun setToken(type: Preferences.Key<String>, token: String) = viewModelScope.launch {
        authRepository.setToken(type, token)
    }

}