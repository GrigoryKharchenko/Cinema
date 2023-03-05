package kinopoisk.cinema.data.network.response

import com.google.gson.annotations.SerializedName

class ActorResponse(
    @SerializedName("personId") val id: Int,
    @SerializedName("posterUrl") val photo: String,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String,
    @SerializedName("profession") val profession: String,
    @SerializedName("films") val films: List<BestFilmsActorResponse>
)

class BestFilmsActorResponse(
    @SerializedName("fillmId") val filmsId: Int,
    @SerializedName("nameRu") val nameFilmRu: String?,
    @SerializedName("nameEn") val nameFilmEn: String,
    @SerializedName("rating") val rating: String?,
)
