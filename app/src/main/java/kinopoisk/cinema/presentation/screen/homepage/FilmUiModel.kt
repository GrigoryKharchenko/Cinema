package kinopoisk.cinema.presentation.screen.homepage

import androidx.annotation.StringRes
import kinopoisk.cinema.R

data class CategoryUiModel(
    @StringRes val category: Int,
    val films: List<TypeCardCategoryUiModel>
)

sealed interface TypeCardCategoryUiModel {

    data class FilmUiModel(
        val poster: String,
        val rating: String,
        val isViewed: Boolean,
        val name: String,
        // TODO: жанр обязательное поле в апишке. почему на юае оно стало необязательным?
        val genre: String?,
        val isVisibleRating: Boolean,
    ) : TypeCardCategoryUiModel

    data class FooterUiModel(
        @StringRes val text: Int = R.string.home_show_all
    ) : TypeCardCategoryUiModel


}
