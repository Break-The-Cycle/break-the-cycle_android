package kau.brave.breakthecycle.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BraveResponse<T>(
    @Json(name = "message")
    val message: String,
    @Json(name = "code")
    val code: Int,
    val data: T? = null,
)