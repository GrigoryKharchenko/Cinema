package kinopoisk.cinema.presentation.screen.staff

import kinopoisk.cinema.presentation.screen.filmdetail.TypeStaff
import java.io.Serializable

data class TypeTitleStaff(
    val titleStaff: String,
    val filmId: Int,
    val typeStaff: TypeStaff
) : Serializable
