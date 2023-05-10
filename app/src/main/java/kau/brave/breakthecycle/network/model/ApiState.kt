package kau.brave.breakthecycle.network.model

import kau.brave.breakthecycle.data.response.BraveResponse


sealed class ApiState<out T : Any> {
    data class Success<T : Any>(val data: T) : ApiState<T>()
    data class Error(val errorResponse: BraveResponse<String>) : ApiState<Nothing>()
    data class NotResponse(val message: String?, val exception: Throwable? = null) :
        ApiState<Nothing>()

    object Loading : ApiState<Nothing>()

    fun onSuccess(onSuccess: (T) -> Unit) {
        if (this is Success) {
            onSuccess(this@ApiState.data)
        }
    }

    fun onError(onError: (BraveResponse<String>) -> Unit) {
        if (this is Error) {
            onError(this@ApiState.errorResponse)
        }
        if (this is NotResponse) {
            onError(BraveResponse("500", message ?: "알 수 없는 오류"))
        }
    }

    fun onLoading(onLoading: () -> Unit) {
        if (this is Loading) {
            onLoading()
        }
    }

}