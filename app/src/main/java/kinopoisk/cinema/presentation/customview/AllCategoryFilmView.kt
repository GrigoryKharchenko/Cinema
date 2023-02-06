package kinopoisk.cinema.presentation.customview

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import kinopoisk.cinema.R
import kinopoisk.cinema.data.TypeCategories
import kinopoisk.cinema.databinding.ItemAllCategoryBinding
import kinopoisk.cinema.presentation.screen.homepage.CategoryUiModel
import kinopoisk.cinema.presentation.screen.homepage.certaincategory.TypeCardCategoryAdapter

class AllCategoryFilmView constructor(
    context: Context,
) : ConstraintLayout(context) {

    fun initUi(
        categoryUiModel: CategoryUiModel,
        onFilmClick: (Int) -> Unit,
        onShowAllClick: (TypeCategories) -> Unit
    ) {
        val adapter = TypeCardCategoryAdapter(onFilmClick = onFilmClick, onShowAllClick = {
            onShowAllClick(categoryUiModel.typeCategory)
        })
        ItemAllCategoryBinding.bind(inflate(context, R.layout.item_all_category, this)).apply {
            tvCategory.text = categoryUiModel.typeCategory.nameCategory
            rvCertainCategory.adapter = adapter
            tvAll.setOnClickListener {
                onShowAllClick(categoryUiModel.typeCategory)
            }
            adapter.submitList(categoryUiModel.films)
        }
    }
}
