package kinopoisk.cinema.presentation.screen.filmdetail.adpters.similar

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemCertainCategoryBinding
import kinopoisk.cinema.extension.inflate
import kinopoisk.cinema.extension.loadCropImage

class SimilarAdapter : ListAdapter<SimilarUiModel, SimilarViewHolder>(SimilarDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder =
        SimilarViewHolder(ItemCertainCategoryBinding.bind(parent.inflate(R.layout.item_certain_category)))

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class SimilarViewHolder(private val binding: ItemCertainCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(similarUiModel: SimilarUiModel) {
        with(binding) {
            ivPreview.loadCropImage(similarUiModel.poster)
            tvName.text = similarUiModel.name
            tvRating.isVisible = false
        }
    }
}

class SimilarDiffUtil : DiffUtil.ItemCallback<SimilarUiModel>() {
    override fun areItemsTheSame(oldItem: SimilarUiModel, newItem: SimilarUiModel): Boolean =
        newItem.id == oldItem.id

    override fun areContentsTheSame(oldItem: SimilarUiModel, newItem: SimilarUiModel): Boolean =
        newItem == oldItem
}
