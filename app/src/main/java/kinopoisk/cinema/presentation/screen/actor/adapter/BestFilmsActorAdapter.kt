package kinopoisk.cinema.presentation.screen.actor.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemCertainCategoryBinding
import kinopoisk.cinema.extension.inflate
import kinopoisk.cinema.extension.loadCropImage
import kinopoisk.cinema.presentation.screen.actor.BestFilmsActorModel

class BestFilmsActorAdapter :
    ListAdapter<BestFilmsActorModel, BestFilmsActorViewHolder>(BestFilmsActorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestFilmsActorViewHolder =
        BestFilmsActorViewHolder(ItemCertainCategoryBinding.bind(parent.inflate(R.layout.item_certain_category)))

    override fun onBindViewHolder(holder: BestFilmsActorViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class BestFilmsActorViewHolder(private val binding: ItemCertainCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        bestFilmsActorModel: BestFilmsActorModel
    ) {
        with(binding) {
            tvRating.text = bestFilmsActorModel.rating
            tvName.text = bestFilmsActorModel.nameFilm
            ivPreview.loadCropImage("")
        }
    }
}

class BestFilmsActorDiffUtil : DiffUtil.ItemCallback<BestFilmsActorModel>() {

    override fun areItemsTheSame(oldItem: BestFilmsActorModel, newItem: BestFilmsActorModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: BestFilmsActorModel, newItem: BestFilmsActorModel): Boolean =
        oldItem == newItem
}
