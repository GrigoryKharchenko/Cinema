package kinopoisk.cinema.presentation.screen.homepage.allcategory

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kinopoisk.cinema.data.TypeCategories
import kinopoisk.cinema.presentation.customview.AllCategoryFilmView
import kinopoisk.cinema.presentation.screen.homepage.CategoryUiModel

class AllCategoryAdapter(
    private val onFilmClick: (Int) -> Unit,
    private val onShowAllClick: (TypeCategories) -> Unit
) : ListAdapter<CategoryUiModel, AllCategoryViewHolder>(AllCategoryDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCategoryViewHolder =
        AllCategoryViewHolder(AllCategoryFilmView(parent.context))

    override fun onBindViewHolder(holder: AllCategoryViewHolder, position: Int) =
        holder.bind(getItem(position), onFilmClick, onShowAllClick)
}

class AllCategoryViewHolder(private val allCategoryFilmView: AllCategoryFilmView) :
    RecyclerView.ViewHolder(allCategoryFilmView.rootView) {

    fun bind(
        categoryUiModel: CategoryUiModel,
        onFilmClick: (Int) -> Unit,
        onShowAllClick: (TypeCategories) -> Unit
    ) {
        allCategoryFilmView.initUi(categoryUiModel, onFilmClick, onShowAllClick)
    }
}

class AllCategoryDiffUtil : DiffUtil.ItemCallback<CategoryUiModel>() {
    override fun areItemsTheSame(oldItem: CategoryUiModel, newItem: CategoryUiModel): Boolean =
        oldItem.typeCategory.nameCategory == newItem.typeCategory.nameCategory

    override fun areContentsTheSame(oldItem: CategoryUiModel, newItem: CategoryUiModel): Boolean =
        oldItem == newItem
}
