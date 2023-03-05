package kinopoisk.cinema.presentation.screen.homepage

import androidx.annotation.StringRes
import kinopoisk.cinema.R
import kinopoisk.cinema.data.TypeCategories

data class CategoryUiModel(
    val typeCategory: TypeCategories,
    val films: List<TypeCardCategoryUiModel>
)

sealed interface TypeCardCategoryUiModel {

    data class FilmUiModel(
        val id:Int,
        val poster: String,
        val rating: String,
        val isViewed: Boolean,
        val name: String,
        val genre: String?,
        val isVisibleRating: Boolean,
    ) : TypeCardCategoryUiModel

    data class FooterUiModel(
        @StringRes val text: Int = R.string.home_show_all
    ) : TypeCardCategoryUiModel
}
