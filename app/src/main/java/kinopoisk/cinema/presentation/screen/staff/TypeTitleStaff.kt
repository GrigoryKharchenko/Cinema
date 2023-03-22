package kinopoisk.cinema.presentation.screen.staff

import androidx.annotation.StringRes
import kinopoisk.cinema.R
import kinopoisk.cinema.presentation.screen.filmdetail.TypeStaff
import java.io.Serializable

data class TypeTitleStaff(
    @StringRes val titleStaff: Int,
    val filmId: Int,
    val typeStaff: TypeStaff
) : Serializable {

    companion object {
        fun createActor(filmId: Int): TypeTitleStaff =
            TypeTitleStaff(
                titleStaff = R.string.film_detail_filmed,
                filmId = filmId,
                typeStaff = TypeStaff.ACTOR
            )

        fun createStaff(filmId: Int): TypeTitleStaff =
            TypeTitleStaff(
                titleStaff = R.string.film_detail_film_worked,
                filmId = filmId,
                typeStaff = TypeStaff.ALL
            )
    }
}
