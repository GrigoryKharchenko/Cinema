package kinopoisk.cinema.presentation.screen.homepage.certaincategory

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemCertainCategoryBinding
import kinopoisk.cinema.databinding.ItemShowAllBinding
import kinopoisk.cinema.extension.inflate
import kinopoisk.cinema.extension.loadCropImage
import kinopoisk.cinema.presentation.screen.homepage.TypeCardCategoryUiModel

class TypeCardCategoryAdapter(
    private val onFilmClick: (Int) -> Unit,
    private val onShowAllClick: () -> Unit
) :
    ListAdapter<TypeCardCategoryUiModel, TypeCardCategoryViewHolder>(TypeCardCategoryDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeCardCategoryViewHolder {
        return when (viewType) {
            R.layout.item_certain_category -> TypeCardCategoryViewHolder.CertainCategoryViewHolder(
                ItemCertainCategoryBinding.bind(parent.inflate(R.layout.item_certain_category))
            )
            R.layout.item_show_all -> TypeCardCategoryViewHolder.FooterViewHolder(
                ItemShowAllBinding.bind(parent.inflate(R.layout.item_show_all))
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: TypeCardCategoryViewHolder, position: Int) {
        when (holder) {
            is TypeCardCategoryViewHolder.CertainCategoryViewHolder ->
                holder.bind(getItem(position) as TypeCardCategoryUiModel.FilmUiModel, onFilmClick)
            is TypeCardCategoryViewHolder.FooterViewHolder ->
                holder.bind(onShowAllClick)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TypeCardCategoryUiModel.FooterUiModel ->
                R.layout.item_show_all
            else ->
                R.layout.item_certain_category
        }
    }
}

sealed class TypeCardCategoryViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class CertainCategoryViewHolder(
        private val binding: ItemCertainCategoryBinding,
    ) : TypeCardCategoryViewHolder(binding) {
        fun bind(
            certainCategory: TypeCardCategoryUiModel.FilmUiModel,
            onFilmClick: (Int) -> Unit
        ) {
            with(binding) {
                root.setOnClickListener { onFilmClick(certainCategory.id) }
                ivPreview.loadCropImage(image = certainCategory.poster)
                tvRating.text = certainCategory.rating
                tvName.text = certainCategory.name
                tvGenre.text = certainCategory.genre ?: itemView.context.getString(R.string.unknown)
                ivViewed.isVisible = certainCategory.isViewed
                tvRating.isVisible = certainCategory.isVisibleRating
            }
        }
    }

    class FooterViewHolder(private val binding: ItemShowAllBinding) : TypeCardCategoryViewHolder(binding) {
        fun bind(
            onShowAllClick: () -> Unit,
        ) {
            with(binding) {
                fabShowAll.setOnClickListener {
                    onShowAllClick()
                }
            }
        }
    }
}

class TypeCardCategoryDiffUtil : DiffUtil.ItemCallback<TypeCardCategoryUiModel>() {
    override fun areItemsTheSame(oldItem: TypeCardCategoryUiModel, newItem: TypeCardCategoryUiModel): Boolean =
        if (oldItem is TypeCardCategoryUiModel.FilmUiModel && newItem is TypeCardCategoryUiModel.FilmUiModel)
            oldItem.name == newItem.name
        else
            true

    override fun areContentsTheSame(oldItem: TypeCardCategoryUiModel, newItem: TypeCardCategoryUiModel): Boolean =
        oldItem == newItem
}
