package kinopoisk.cinema.data.network.response

import com.google.gson.annotations.SerializedName

class SearchFilmsResponse(
    @SerializedName("items") val differentFilms: List<SearchFilmResponse>
)

class SearchFilmResponse(
    @SerializedName("kinopoiskId") val id: Int,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String?,
    @SerializedName("nameOriginal") val nameOriginal: String,
    @SerializedName("ratingKinopoisk") val ratingKinopoisk: String?,
    @SerializedName("ratingImdb") val ratingImdb: String,
    @SerializedName("year") val year: String,
    @SerializedName("posterUrl") val poster: String,
    @SerializedName("genres") val genres: List<GenreResponse>
)
