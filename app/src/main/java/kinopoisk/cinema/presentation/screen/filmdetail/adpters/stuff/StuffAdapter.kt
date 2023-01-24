package kinopoisk.cinema.presentation.screen.filmdetail.adpters.stuff

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemStuffBinding
import kinopoisk.cinema.extension.inflate
import kinopoisk.cinema.extension.loadCropImage
import kinopoisk.cinema.presentation.screen.filmdetail.model.StuffModel

class StuffAdapter : ListAdapter<StuffModel, StuffViewHolder>(StuffDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StuffViewHolder =
        StuffViewHolder(ItemStuffBinding.bind(parent.inflate(R.layout.item_stuff)))

    override fun onBindViewHolder(holder: StuffViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class StuffViewHolder(private val binding: ItemStuffBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(stuffModel: StuffModel,) {
        with(binding) {
            tvNameActor.text = stuffModel.name ?: itemView.context.getString(R.string.unknown)
            tvActorCharacter.text = stuffModel.character ?: itemView.context.getString(R.string.unknown)
            ivPhotoActor.loadCropImage(stuffModel.photo)
        }
    }
}

class StuffDiffUtil : DiffUtil.ItemCallback<StuffModel>() {
    override fun areItemsTheSame(oldItem: StuffModel, newItem: StuffModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: StuffModel, newItem: StuffModel): Boolean =
        oldItem == newItem
}
