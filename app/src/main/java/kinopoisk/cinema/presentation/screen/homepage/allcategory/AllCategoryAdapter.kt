package kinopoisk.cinema.presentation.screen.homepage.allcategory

import android.os.Parcelable
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

    private val scrollStates = mutableMapOf<Int, Parcelable?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCategoryViewHolder =
        AllCategoryViewHolder(AllCategoryFilmView(parent.context))

    override fun onBindViewHolder(holder: AllCategoryViewHolder, position: Int) {
        holder.bind(getItem(position), onFilmClick, onShowAllClick)
        if (scrollStates[holder.layoutPosition] != null) {
            scrollStates[holder.layoutPosition]?.let { holder.setState(it) }
        }
    }

    override fun onViewRecycled(holder: AllCategoryViewHolder) {
        super.onViewRecycled(holder)
        scrollStates[holder.layoutPosition] = holder.getState()
    }
}

class AllCategoryViewHolder(private val allCategoryFilmView: AllCategoryFilmView) :
    RecyclerView.ViewHolder(allCategoryFilmView.rootView) {

    fun bind(
        categoryUiModel: CategoryUiModel,
        onFilmClick: (Int) -> Unit,
        onShowAllClick: (TypeCategories) -> Unit,
    ) {
        allCategoryFilmView.initUi(categoryUiModel, onFilmClick, onShowAllClick)
    }

    fun getState() = allCategoryFilmView.getRecyclerSaveInstanceState()

    fun setState(state: Parcelable) = allCategoryFilmView.setRecyclerRestoreInstanceState(state)
}

class AllCategoryDiffUtil : DiffUtil.ItemCallback<CategoryUiModel>() {
    override fun areItemsTheSame(oldItem: CategoryUiModel, newItem: CategoryUiModel): Boolean =
        oldItem.typeCategory.nameCategory == newItem.typeCategory.nameCategory

    override fun areContentsTheSame(oldItem: CategoryUiModel, newItem: CategoryUiModel): Boolean =
        oldItem == newItem
}
