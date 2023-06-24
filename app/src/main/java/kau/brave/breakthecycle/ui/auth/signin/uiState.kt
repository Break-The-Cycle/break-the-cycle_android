package kau.brave.breakthecycle.ui.auth.signin

import kau.brave.breakthecycle.ui.model.VerificationStatus


data class SignInPhoneVerifyScreenUiState(
    val phone: String = "",
    val verifyCode: String = "",
    val isVerfified: Boolean = false,
    val retryTime: Int = -1
)

data class SignInIdPasswordScreenUiState(
    val id: String = "",
    val idDupCheck: VerificationStatus = VerificationStatus.NONE,
    val password: String = "",
    val passwordRegexCheck: VerificationStatus = VerificationStatus.NONE,
    val secondPassword: String = "",
    val passwordCorrectCheck: VerificationStatus = VerificationStatus.NONE,
    val name: String = ""

)