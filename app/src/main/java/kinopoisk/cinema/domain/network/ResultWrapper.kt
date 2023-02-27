package kinopoisk.cinema.domain.network

import kinopoisk.cinema.data.network.response.ErrorResponse

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(
        val codeError: Int? = null,
        val errorMessage: ErrorResponse? = null
    ) : ResultWrapper<Nothing>()

    object NetworkError : ResultWrapper<Nothing>()
}
