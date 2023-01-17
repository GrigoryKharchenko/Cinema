package kinopoisk.cinema.data.network.response

import com.google.gson.annotations.SerializedName

data class FilmsResponse(
    @SerializedName("items") val anotherFilms: List<FilmResponse>,
    @SerializedName("films") val topFilms: List<FilmResponse>
)

data class FilmResponse(
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("filmId") val id: Int,
    @SerializedName("nameEn") val nameEn: String?,
    @SerializedName("nameOriginal") val nameOriginal: String,
    @SerializedName("ratingKinopoisk") val ratingKinopoisk: String?,
    @SerializedName("rating") val rating: String?,
    @SerializedName("posterUrl") val poster: String,
    @SerializedName("premiereRu") val dateRelease: String,
    @SerializedName("genres") val genre: List<GenreResponse>,
)

data class GenreResponse(
    @SerializedName("genre") val genre: String
)
