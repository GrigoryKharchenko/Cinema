package kinopoisk.cinema.presentation.screen.staff

import java.io.Serializable

sealed class TypeTitleStaff(
    open val titleStaff: String,
    open val filmId: Int,
) : Serializable {

    data class Actors(
        override val titleStaff: String,
        override val filmId: Int
    ) : TypeTitleStaff(titleStaff, filmId)

    data class FilmMakers(
        override val titleStaff: String,
        override val filmId: Int
    ) : TypeTitleStaff(titleStaff, filmId)
}
