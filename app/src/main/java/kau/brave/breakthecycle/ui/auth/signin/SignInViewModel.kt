package kau.brave.breakthecycle.ui.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kau.brave.breakthecycle.ui.auth.model.VerificationStatus
import kotlinx.coroutines.flow.*
import java.util.*

class SignInViewModel : ViewModel() {

    /** 핸드폰 인증 State */
    private val _verifyPhoneNumber = MutableStateFlow("")
    private val _verifyCode = MutableStateFlow("")
    private val _isVerified = MutableStateFlow(false)
    private val _retryTime = MutableStateFlow(0)

    /** 아이디 패드워드 인증 State */
    private val _nickname = MutableStateFlow("")
    private val _nicknameDupCheck = MutableStateFlow(VerificationStatus.NONE)
    private val _password = MutableStateFlow("")
    private val _secondPassword = MutableStateFlow("")
    private val _passwordCorrectCheck = MutableStateFlow(VerificationStatus.NONE)

    private var timer = Timer()
    private var timerTask: TimerTask = object : TimerTask() {
        override fun run() {
            _retryTime.update { _retryTime.value - 1 }
            if (_retryTime.value <= 0) {
                timer.cancel()
            }
        }
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
        _verifyPhoneNumber, _verifyCode, _isVerified, _retryTime
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
        _nickname, _nicknameDupCheck, _password, _secondPassword, _passwordCorrectCheck
    ) { nickname, nicknameDupCheck, password, secondPassword, passwordCorrectCheck ->
        SignInIdPasswordScreenUiState(
            nickname = nickname,
            nicknameDupCheck = nicknameDupCheck,
            password = password,
            secondPassword = secondPassword,
            passwordCorrectCheck = passwordCorrectCheck
        )
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), SignInIdPasswordScreenUiState()
    )

    fun verifyCode() {
        if (_retryTime.value <= 0) {
            // onFailed
        } else {
            // TODO 전화번호 검증수행
            _isVerified.value = true
        }
    }

    fun sendVerficyCode() {
        // TODO 전화번호 인증 번호 보내기
        resetTimer()
    }

    fun updateVerifyPhoneNumber(phoneNumber: String) {
        _verifyPhoneNumber.value = phoneNumber
    }

    fun updateVerifyCode(code: String) {
        _verifyCode.value = code
    }

    fun updateNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun updatePassword(password: String) {
        _password.value = password
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


    companion object {
        const val DEFAULT_RETRY_TIME = 180
    }
}