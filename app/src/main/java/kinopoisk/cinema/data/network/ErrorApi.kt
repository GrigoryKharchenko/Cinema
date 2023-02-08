package kinopoisk.cinema.data.network

import com.google.gson.Gson
import kinopoisk.cinema.data.network.response.ErrorResponse
import kinopoisk.cinema.domain.network.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = errorApi(throwable)
                    ResultWrapper.GenericError(code, errorResponse)
                }
                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }
}

fun errorApi(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.string().let {
            Gson().fromJson(it, ErrorResponse::class.java)
        }
    } catch (exception: Exception) {
        null
    }
}
