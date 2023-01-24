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
import kinopoisk.cinema.presentation.screen.filmdetail.model.SimilarFilmModel

class SimilarFilmAdapter : ListAdapter<SimilarFilmModel, SimilarFilmViewHolder>(SimilarFilmDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarFilmViewHolder =
        SimilarFilmViewHolder(ItemCertainCategoryBinding.bind(parent.inflate(R.layout.item_certain_category)))

    override fun onBindViewHolder(holder: SimilarFilmViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class SimilarFilmViewHolder(private val binding: ItemCertainCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(similarFilmModel: SimilarFilmModel) {
        with(binding) {
            ivPreview.loadCropImage(similarFilmModel.poster)
            tvName.text = similarFilmModel.name
            tvRating.isVisible = false
        }
    }
}

class SimilarFilmDiffUtil : DiffUtil.ItemCallback<SimilarFilmModel>() {
    override fun areItemsTheSame(oldItem: SimilarFilmModel, newItem: SimilarFilmModel): Boolean =
        newItem.id == oldItem.id

    override fun areContentsTheSame(oldItem: SimilarFilmModel, newItem: SimilarFilmModel): Boolean =
        newItem == oldItem
}
