package kinopoisk.cinema.data.network.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message") val message: String,
    @SerializedName("error") val error: String,
    @SerializedName("status") val status: String
)
