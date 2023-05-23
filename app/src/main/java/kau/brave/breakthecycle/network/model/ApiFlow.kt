package kau.brave.breakthecycle.network.model


import kau.brave.breakthecycle.data.response.BraveResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

fun <T : Any> apiFlow(apiFunc: suspend () -> Response<BraveResponse<T>>): Flow<ApiState<BraveResponse<T>>> =
    flow {
        try {
            val res = apiFunc.invoke()
            if (res.isSuccessful) {
                emit(ApiState.Success(res.body() ?: throw NullPointerException()))
            } else {
                val errorBody = res.errorBody() ?: throw NullPointerException()
                emit(ApiState.Error(GsonHelper.getErrorMessage(errorBody.string())))
            }
        } catch (e: Exception) {
            emit(ApiState.NotResponse(message = e.message ?: "", exception = e))
        }
    }.flowOn(Dispatchers.IO)
