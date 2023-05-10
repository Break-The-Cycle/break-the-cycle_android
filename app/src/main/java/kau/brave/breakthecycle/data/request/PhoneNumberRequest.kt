package kau.brave.breakthecycle.data.request

import com.squareup.moshi.Json

data class PhoneNumberRequest(
    @Json(name = "phoneNumber")
    val phoneNumber: String
)