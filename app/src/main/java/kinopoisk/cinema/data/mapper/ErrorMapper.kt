package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.ErrorResponse

fun ErrorResponse.mpaToMessageError(): String =
    "Ошибка $status.$textError"
