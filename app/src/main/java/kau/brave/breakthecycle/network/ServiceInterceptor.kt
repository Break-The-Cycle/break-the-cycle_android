package kau.brave.breakthecycle.network

import okhttp3.Interceptor
import okhttp3.Response

class ServiceInterceptor : Interceptor {

    companion object {
        var usePersonId: Int = -1
        var accessToken: String? = null
        var refreshToken: String = ""
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (request.header("No-Authentication") == null) {
            if (!accessToken.isNullOrEmpty()) {
                val finalToken = "Bearer $accessToken"
                request = request.newBuilder().apply {
                    addHeader("Authorization", finalToken)
                }.build()
            }
        }
        return chain.proceed(request)
    }
}