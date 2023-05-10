package kau.brave.breakthecycle.data.request

import com.squareup.moshi.Json

data class PhoneNumberWithCertificationNumberRequest(
    @Json(name = "phoneNumber")
    val phoneNumber: String,
    @Json(name = "certificationNumber")
    val certificationNumber: String
)