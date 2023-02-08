package kinopoisk.cinema.domain.network

import kinopoisk.cinema.data.network.response.ErrorResponse

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null): ResultWrapper<Nothing>() {
        fun getMessageOrNull() = error?.error?.firstOrNull()
    }
    object NetworkError: ResultWrapper<Nothing>()

    fun isError() = this is GenericError || this is NetworkError
}
