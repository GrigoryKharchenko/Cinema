package kinopoisk.cinema.data.network.response

import com.google.gson.annotations.SerializedName

data class FilmsResponse(
    // TODO: а они не нулабельные приходят для разных запросов а пустые?
    @SerializedName("items") val anotherFilms: List<FilmResponse>,
    @SerializedName("films") val topFilms: List<FilmResponse>
)

// TODO: для разных запросов ответы отливаются. например для топа и для премьер. в премьерах нет filmId но есть  kinopoiskId
//  отличаются не только имена но и обязательность полей
//  у тебя сейчас в премьерах все фильмы с id=0
data class FilmResponse(
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("filmId") val id: Int,
    @SerializedName("nameEn") val nameEn: String?,
    @SerializedName("nameOriginal") val nameOriginal: String,
    @SerializedName("ratingKinopoisk") val ratingKinopoisk: String?,
    @SerializedName("rating") val rating: String?,
    // TODO: тут обязательно целиковый постер тянуть? превьюхи недостаточно в текущей реализации?
    @SerializedName("posterUrl") val poster: String,
    @SerializedName("premiereRu") val dateRelease: String,
    @SerializedName("genres") val genre: List<GenreResponse>,
)

data class GenreResponse(
    @SerializedName("genre") val genre: String
)
