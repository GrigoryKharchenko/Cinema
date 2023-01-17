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

    private val binding = ItemAllCategoryBinding.bind(inflate(context, R.layout.item_all_category, this))
    private val adapter = TypeCardCategoryAdapter()

    fun initUi(categoryUiModel: CategoryUiModel) {
        with(binding) {
            tvCategory.text = context.getString(categoryUiModel.category)
            rvCertainCategory.adapter = adapter
            adapter.submitList(categoryUiModel.films)
        }
    }
}
