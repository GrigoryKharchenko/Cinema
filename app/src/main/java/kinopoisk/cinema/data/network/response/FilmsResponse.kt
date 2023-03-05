package kinopoisk.cinema.data.network.response

import com.google.gson.annotations.SerializedName

class FilmsResponse(
    @SerializedName("items") val anotherFilms: List<FilmResponse>,
    @SerializedName("films") val topFilms: List<FilmResponse>,
)

class FilmResponse(
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String?,
    @SerializedName("nameOriginal") val nameOriginal: String,
    @SerializedName("ratingKinopoisk") val ratingKinopoisk: String?,
    @SerializedName("rating") val rating: String?,
    @SerializedName("posterUrlPreview") val poster: String,
    @SerializedName("premiereRu") val dateRelease: String,
    @SerializedName("genres") val genres: List<GenreResponse>,
    @SerializedName("kinopoiskId") val kinopoiskId: Int?,
    @SerializedName("filmId") val id: Int,
    @SerializedName("countries") val countries: List<CountryResponse>,
)

class GenreResponse(
    @SerializedName("genre") val genre: String,
)
