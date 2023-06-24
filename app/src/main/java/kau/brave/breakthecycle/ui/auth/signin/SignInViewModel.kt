package kau.brave.breakthecycle.ui.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kau.brave.breakthecycle.data.request.PhoneAndCertificationNumber
import kau.brave.breakthecycle.data.request.PhoneNumber
import kau.brave.breakthecycle.data.request.RegisterRequest
import kau.brave.breakthecycle.domain.repository.AuthRepository
import kau.brave.breakthecycle.network.ServiceInterceptor
import kau.brave.breakthecycle.ui.model.VerificationStatus
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    /** 핸드폰 인증 State */
    private val _verifyPhoneNumber = MutableStateFlow("")
    private val _certificationCode = MutableStateFlow("")
    private val _isVerified = MutableStateFlow(false)
    private val _retryTime = MutableStateFlow(0)

    /** 아이디 패드워드 인증 State */
    private val _name = MutableStateFlow("")
    private val _id = MutableStateFlow("")
    private val _idDupCheck = MutableStateFlow(VerificationStatus.NONE)
    private val _password = MutableStateFlow("")
    private val _passwordRegexCheck = MutableStateFlow(VerificationStatus.NONE)
    private val _secondPassword = MutableStateFlow("")
    private val _passwordCorrectCheck = MutableStateFlow(VerificationStatus.NONE)

    /** 문자인증 타이머 */
    private var timer = Timer()
    private var timerTask: TimerTask = object : TimerTask() {
        override fun run() {
            _retryTime.update { _retryTime.value - 1 }
            if (_retryTime.value <= 0) {
                timer.cancel()
            }
        }
    }

    private fun stopTimer() {
        timer.cancel()
    }

    private fun startTimer() {
        _retryTime.value = DEFAULT_RETRY_TIME
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                _retryTime.update { _retryTime.value - 1 }
                if (_retryTime.value <= 0) {
                    timer.cancel()
                }
            }
        }
        timer.schedule(timerTask, 0, 1000);
    }

    private fun resetTimer() {
        _retryTime.value = DEFAULT_RETRY_TIME
        startTimer()
    }

    val verifyPhoneUiState: StateFlow<SignInPhoneVerifyScreenUiState> = combine(
        _verifyPhoneNumber, _certificationCode, _isVerified, _retryTime
    ) { phone, verifyCode, isVerified, retryTime ->
        SignInPhoneVerifyScreenUiState(
            phone = phone,
            verifyCode = verifyCode,
            isVerfified = isVerified,
            retryTime = retryTime
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), SignInPhoneVerifyScreenUiState()
    )

    val signInIdPasswordScreenUiState: StateFlow<SignInIdPasswordScreenUiState> = combine(
        _id,
        _idDupCheck,
        _password,
        _secondPassword,
        _passwordCorrectCheck,
        _passwordRegexCheck,
        _name
    ) {
        SignInIdPasswordScreenUiState(
            id = it[0] as String,
            idDupCheck = it[1] as VerificationStatus,
            password = it[2] as String,
            secondPassword = it[3] as String,
            passwordCorrectCheck = it[4] as VerificationStatus,
            passwordRegexCheck = it[5] as VerificationStatus,
            name = it[6] as String
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), SignInIdPasswordScreenUiState()
    )

    fun confirmCertificationCode(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit,
    ) = viewModelScope.launch {
        if (_retryTime.value <= 0) {
            onError("인증번호를 다시 받아주세요.")
            return@launch
        }
        authRepository.confirmCetificationCode(
            PhoneAndCertificationNumber(
                phoneNumber = _verifyPhoneNumber.value,
                certificationNumber = _certificationCode.value
            )
        ).collectLatest { apiState ->
            apiState.onSuccess {
                onSuccess("인증에 성공했습니다.")
                stopTimer()
                _isVerified.value = true
            }
            apiState.onError(onError)
        }
    }

    fun sendCertificationCode(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {
        resetTimer()
        authRepository.sendCertificationCode(PhoneNumber(_verifyPhoneNumber.value))
            .collectLatest { apiState ->
                apiState.onSuccess {
                    onSuccess()
                }
                apiState.onError(onError)
            }
    }

    fun checkIdDuplication(
        onError: (String) -> Unit
    ) = viewModelScope.launch {
        authRepository.checkDupNickname(_id.value).collectLatest { apiState ->
            apiState.onSuccess {
                _idDupCheck.value = VerificationStatus.SUCCESS
            }
            apiState.onError { onError(it) }
        }
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _verifyPhoneNumber.value = phoneNumber
    }

    fun updateCertificationCode(code: String) {
        _certificationCode.value = code
    }

    fun updateId(id: String) {
        _id.value = id
        _idDupCheck.value = VerificationStatus.NONE
    }

    fun updateName(name: String) {
        _name.value = name
    }

    fun updatePassword(password: String) {
        _password.value = password
        _passwordRegexCheck.value = if (password.isEmpty()) VerificationStatus.NONE else {
            if (password.length in 8..12 && password.matches(Regex(".*\\d.*")) && password.matches(
                    Regex(".*[a-z].*")
                ) && password.matches(Regex(".*[A-Z].*")) && password.matches(Regex(".*[!@#\$%^&*(),.?\":{}|<>].*"))
            ) VerificationStatus.SUCCESS else VerificationStatus.ERROR
        }
    }

    fun updateSecondPassword(secondPassword: String) {
        _secondPassword.value = secondPassword
        if (_secondPassword.value.isEmpty()) {
            _passwordCorrectCheck.value = VerificationStatus.NONE
        } else if (_password.value == _secondPassword.value) {
            _passwordCorrectCheck.value = VerificationStatus.SUCCESS
        } else if (_password.value != _secondPassword.value) {
            _passwordCorrectCheck.value = VerificationStatus.ERROR
        }
    }

    fun signIn(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) = viewModelScope.launch {
        authRepository.signIn(
            RegisterRequest(
                name = _name.value,
                phoneNumber = _verifyPhoneNumber.value,
                password = _password.value,
                password2 = _secondPassword.value,
                loginId = _id.value
            )
        ).collectLatest { apiState ->
            apiState.onSuccess {
                if (it.data?.id == null) {
                    onError("회원가입에 실패했습니다.")
                } else {
                    ServiceInterceptor.usePersonId = it.data.id
                    onSuccess()
                }
            }
            apiState.onError { onError(it) }
        }
    }

    companion object {
        const val DEFAULT_RETRY_TIME = 180
    }
}