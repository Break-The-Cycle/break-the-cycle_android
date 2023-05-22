package kau.brave.breakthecycle.network.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kau.brave.breakthecycle.data.response.ErrorResponse

object GsonHelper {
    val gson = Gson()

    inline fun <reified T> fromJson(json: String): T {
        return gson.fromJson(json, object : TypeToken<T>() {}.type)
    }

    fun getErrorMessage(errorBody: String): String {
        return gson.fromJson(errorBody, ErrorResponse::class.java).message
    }
}
