package kau.brave.breakthecycle.ui.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kau.brave.breakthecycle.ui.model.VerificationStatus
import kotlinx.coroutines.flow.*
import java.util.*

class SignInViewModel : ViewModel() {

    /** 핸드폰 인증 State */
    private val _verifyPhoneNumber = MutableStateFlow("")
    private val _verifyCode = MutableStateFlow("")
    private val _isVerified = MutableStateFlow(false)
    private val _retryTime = MutableStateFlow(0)

    /** 아이디 패드워드 인증 State */
    private val _id = MutableStateFlow("")
    private val _idDupCheck = MutableStateFlow(VerificationStatus.NONE)
    private val _password = MutableStateFlow("")
    private val _passwordRegexCheck = MutableStateFlow(VerificationStatus.NONE)
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
        _id, _idDupCheck, _password, _secondPassword, _passwordCorrectCheck, _passwordRegexCheck
    ) {
        SignInIdPasswordScreenUiState(
            id = it[0] as String,
            idDupCheck = it[1] as VerificationStatus,
            password = it[2] as String,
            secondPassword = it[3] as String,
            passwordCorrectCheck = it[4] as VerificationStatus,
            passwordRegexCheck = it[5] as VerificationStatus
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

    fun checkIdDuplication() {
        // TODO 아이디 중복 검사
        _idDupCheck.value = VerificationStatus.SUCCESS
    }

    fun updateVerifyPhoneNumber(phoneNumber: String) {
        _verifyPhoneNumber.value = phoneNumber
    }

    fun updateVerifyCode(code: String) {
        _verifyCode.value = code
    }

    fun updateId(id: String) {
        _id.value = id
        _idDupCheck.value = VerificationStatus.NONE
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


    companion object {
        const val DEFAULT_RETRY_TIME = 180
    }
}