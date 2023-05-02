package kau.brave.breakthecycle.ui.auth.signin

import kau.brave.breakthecycle.ui.auth.model.VerificationStatus
import kau.brave.breakthecycle.ui.auth.signin.SignInViewModel.Companion.DEFAULT_RETRY_TIME


data class SignInPhoneVerifyScreenUiState(
    val phone: String = "",
    val verifyCode: String = "",
    val isVerfified: Boolean = false,
    val retryTime: Int = -1
)

data class SignInIdPasswordScreenUiState(
    val nickname: String = "",
    val nicknameDupCheck: VerificationStatus = VerificationStatus.NONE,
    val password: String = "",
    val secondPassword: String = "",
    val passwordCorrectCheck: VerificationStatus = VerificationStatus.NONE
)