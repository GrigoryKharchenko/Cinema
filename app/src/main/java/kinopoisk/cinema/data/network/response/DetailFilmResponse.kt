package kinopoisk.cinema.data.network.response

import com.google.gson.annotations.SerializedName

class DetailFilmResponse(
    @SerializedName("kinopoiskId") val id: Int,
    @SerializedName("posterUrl") val poster: String,
    @SerializedName("logoUrl") val logo: String?,
    @SerializedName("ratingKinopoisk") val rating: String?,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String?,
    @SerializedName("nameOriginal") val nameOriginal: String,
    @SerializedName("year") val released: String?,
    @SerializedName("genres") val genres: List<GenreResponse>,
    @SerializedName("countries") val countries: List<CountryResponse>,
    @SerializedName("filmLength") val duration: String?,
    @SerializedName("ratingMpaa") val ratingMpaa: String?,
    @SerializedName("ratingAgeLimits") val ratingAgeLimits: String?,
    @SerializedName("shortDescription") val shortDescription: String?,
    @SerializedName("description") val description: String,
    @SerializedName("type") val type: String,
)

class CountryResponse(
    @SerializedName("country") val country: String,
)
