package kinopoisk.cinema.data.network.response

import com.google.gson.annotations.SerializedName

data class SimilarsResponse(
    @SerializedName("total") val total: String,
    @SerializedName("items") val items: List<SimilarResponse>
)

data class SimilarResponse(
    @SerializedName("filmId") val id: Int,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String,
    @SerializedName("posterUrlPreview") val poster: String
)
