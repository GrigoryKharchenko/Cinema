package kinopoisk.cinema.presentation.screen.searchfilter

import androidx.annotation.StringRes
import kinopoisk.cinema.R
import java.io.Serializable

data class TypeFilterScreen(
    @StringRes val typeTitle: Int,
    @StringRes val hintSearchField: Int,
    val typeFilter: TypeFilter
) : Serializable {

    companion object {
        fun createCountry(): TypeFilterScreen =
            TypeFilterScreen(
                typeTitle = R.string.search_filter_title_countries,
                hintSearchField = R.string.search_filter_enter_country,
                typeFilter = TypeFilter.COUNTRY
            )

        fun createGenre(): TypeFilterScreen =
            TypeFilterScreen(
                typeTitle = R.string.search_filter_title_genres,
                hintSearchField = R.string.search_filter_enter_genre,
                typeFilter = TypeFilter.GENRE
            )
    }
}
