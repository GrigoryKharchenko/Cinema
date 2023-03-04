package kinopoisk.cinema.presentation.customview

import android.content.Context
import android.os.Parcelable
import androidx.constraintlayout.widget.ConstraintLayout
import kinopoisk.cinema.R
import kinopoisk.cinema.data.TypeCategories
import kinopoisk.cinema.databinding.ItemAllCategoryBinding
import kinopoisk.cinema.presentation.screen.homepage.CategoryUiModel
import kinopoisk.cinema.presentation.screen.homepage.certaincategory.TypeCardCategoryAdapter

// TODO почему унаслндовался от ConstraintLayout
class AllCategoryFilmView constructor(
    context: Context,
) : ConstraintLayout(context) {

    private val binding = ItemAllCategoryBinding.bind(inflate(context, R.layout.item_all_category, this))

    fun initUi(
        categoryUiModel: CategoryUiModel,
        onFilmClick: (Int) -> Unit,
        onShowAllClick: (TypeCategories) -> Unit
    ) {
        val adapter = TypeCardCategoryAdapter(onFilmClick = onFilmClick, onShowAllClick = {
            onShowAllClick(categoryUiModel.typeCategory)
        })
        with(binding) {
            tvCategory.text = categoryUiModel.typeCategory.nameCategory
            rvCertainCategory.adapter = adapter
            tvAll.setOnClickListener {
                onShowAllClick(categoryUiModel.typeCategory)
            }
            adapter.submitList(categoryUiModel.films)
        }
    }

    fun getRecyclerSaveInstanceState() = binding.rvCertainCategory.layoutManager?.onSaveInstanceState()

    fun setRecyclerRestoreInstanceState(state: Parcelable) =
        binding.rvCertainCategory.layoutManager?.onRestoreInstanceState(state)
}

