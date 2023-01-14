package kinopoisk.cinema.presentation.customview

import android.content.Context
import android.widget.LinearLayout
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemAllCategoryBinding
import kinopoisk.cinema.presentation.screen.homepage.CategoryUiModel
import kinopoisk.cinema.presentation.screen.homepage.certaincategory.TypeCardCategoryAdapter

class AllCategoryFilmView constructor(
    context: Context,
) : LinearLayout(context) {

    fun initUi(categoryUiModel: CategoryUiModel, onFilmClick: (Int) -> Unit) {
        val adapter = TypeCardCategoryAdapter(onFilmClick)
        ItemAllCategoryBinding.bind(inflate(context, R.layout.item_all_category, this)).apply {
            tvCategory.text = context.getString(categoryUiModel.category)
            rvCertainCategory.adapter = adapter
            adapter.submitList(categoryUiModel.films)
        }
    }
}
