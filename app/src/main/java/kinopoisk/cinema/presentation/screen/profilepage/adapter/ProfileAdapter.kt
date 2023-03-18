package kinopoisk.cinema.presentation.screen.profilepage.adapter

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemCertainCategoryBinding
import kinopoisk.cinema.databinding.ItemDeleteHistoryBinding
import kinopoisk.cinema.extension.inflate
import kinopoisk.cinema.extension.loadCropImage
import kinopoisk.cinema.presentation.screen.homepage.TypeCardCategoryUiModel

class ProfileAdapter(
    private val onDelete: () -> Unit
) : ListAdapter<TypeCardCategoryUiModel, ProfileViewHolder>(ProfileDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return when (viewType) {
            R.layout.item_certain_category -> ProfileViewHolder.FilmViewedViewHolder(
                ItemCertainCategoryBinding.bind(parent.inflate(R.layout.item_certain_category))
            )
            R.layout.item_delete_history -> ProfileViewHolder.FooterViewHolder(
                ItemDeleteHistoryBinding.bind(parent.inflate(R.layout.item_delete_history))
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        when (holder) {
            is ProfileViewHolder.FilmViewedViewHolder ->
                holder.bind(getItem(position) as TypeCardCategoryUiModel.FilmUiModel)
            is ProfileViewHolder.FooterViewHolder ->
                holder.bind(onDelete)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TypeCardCategoryUiModel.FooterUiModel ->
                R.layout.item_delete_history
            is TypeCardCategoryUiModel.FilmUiModel ->
                R.layout.item_certain_category
            else ->
                throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }
}

sealed class ProfileViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class FilmViewedViewHolder(
        private val binding: ItemCertainCategoryBinding,
    ) : ProfileViewHolder(binding) {
        fun bind(
            filmViewed: TypeCardCategoryUiModel.FilmUiModel,
        ) {
            with(binding) {
                ivPreview.loadCropImage(image = filmViewed.poster)
                tvRating.text = filmViewed.rating
                tvName.text = filmViewed.name
                tvGenre.text = filmViewed.genre ?: itemView.context.getString(R.string.unknown)
                ivViewed.isVisible = filmViewed.isViewed
                tvRating.isVisible = filmViewed.isVisibleRating
            }
        }
    }

    class FooterViewHolder(private val binding: ItemDeleteHistoryBinding) : ProfileViewHolder(binding) {
        fun bind(onDelete: () -> Unit) {
            binding.fabShowAll.setOnClickListener {
                onDelete()
            }
        }
    }
}

class ProfileDiffUtil : DiffUtil.ItemCallback<TypeCardCategoryUiModel>() {
    override fun areItemsTheSame(oldItem: TypeCardCategoryUiModel, newItem: TypeCardCategoryUiModel): Boolean =
        if (oldItem is TypeCardCategoryUiModel.FilmUiModel && newItem is TypeCardCategoryUiModel.FilmUiModel)
            oldItem.id == newItem.id
        else
            true

    override fun areContentsTheSame(oldItem: TypeCardCategoryUiModel, newItem: TypeCardCategoryUiModel): Boolean =
        oldItem == newItem
}
