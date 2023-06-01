package kau.brave.breakthecycle.ui.auth.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kau.brave.breakthecycle.data.request.LoginRequest
import kau.brave.breakthecycle.domain.domain.SHA256Encoder
import kau.brave.breakthecycle.domain.repository.AuthRepository
import kau.brave.breakthecycle.network.ServiceInterceptor
import kau.brave.breakthecycle.utils.Constants.PREF_ACCESS_TOKEN
import kau.brave.breakthecycle.utils.Constants.PREF_HASHED_PW
import kau.brave.breakthecycle.utils.Constants.PREF_REFRESH_TOKEN
import kau.brave.breakthecycle.utils.Constants.PREF_USER_ID
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val shA256Encoder: SHA256Encoder
) : ViewModel() {

    private val _id = mutableStateOf("")
    val id: State<String> get() = _id

    private val _passwd = mutableStateOf("")
    val passwd: State<String> get() = _passwd

    val updateId = { id: String -> _id.value = id }
    val updatePasswd = { passwd: String -> _passwd.value = passwd }

    private fun setToken(type: Preferences.Key<String>, value: String) = viewModelScope.launch {
        authRepository.setToken(type = type, value = value)
    }

    fun login(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {
        authRepository.login(
            loginRequest = LoginRequest(
                loginId = id.value,
                password = passwd.value
            )
        ).collectLatest { apiState ->
            apiState.onSuccess {
                if (it.data == null) return@onSuccess
                ServiceInterceptor.usePersonId = it.data.userId
                ServiceInterceptor.accessToken = it.data.accessToken
                ServiceInterceptor.refreshToken = it.data.refreshToken
                Log.i("HASHED-PW", shA256Encoder.encode(passwd.value))
                setToken(type = PREF_HASHED_PW, value = shA256Encoder.encode(passwd.value))
                setToken(type = PREF_USER_ID, value = it.data.userId.toString())
                setToken(type = PREF_ACCESS_TOKEN, value = it.data.accessToken)
                setToken(type = PREF_REFRESH_TOKEN, value = it.data.refreshToken)
                onSuccess()
            }
            apiState.onError(onError)
        }
    }

}