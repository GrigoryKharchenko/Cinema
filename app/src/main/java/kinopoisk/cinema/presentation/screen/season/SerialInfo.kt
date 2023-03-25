package kinopoisk.cinema.presentation.screen.season

import java.io.Serializable

data class SerialInfo(
    val nameFilm: String,
    val filmId: Int
) : Serializable {

    companion object {
        fun createFilmInfo(id: Int, nameFilm: String): SerialInfo =
            SerialInfo(nameFilm = nameFilm, filmId = id)
    }
}
