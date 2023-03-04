package kinopoisk.cinema.data.network.response

import com.google.gson.annotations.SerializedName

class ErrorResponse(
    @SerializedName("status") val status: String,
    @SerializedName("error") val textError: String
)
