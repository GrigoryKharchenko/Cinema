package kinopoisk.cinema.presentation.screen.filmdetail.adpters.filmcrew

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemFilmCrewBinding
import kinopoisk.cinema.extension.inflate
import kinopoisk.cinema.extension.loadCropImage

class FilmCrewAdapter : ListAdapter<FilmCrewUiModel, FilmCrewViewHolder>(FilmCrewDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmCrewViewHolder =
        FilmCrewViewHolder(ItemFilmCrewBinding.bind(parent.inflate(R.layout.item_film_crew)))

    override fun onBindViewHolder(holder: FilmCrewViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class FilmCrewViewHolder(private val binding: ItemFilmCrewBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(filmCrewUiModel: FilmCrewUiModel) {
        with(binding) {
            tvNameActor.text = filmCrewUiModel.name ?: itemView.context.getString(R.string.unknown)
            tvActorCharacter.text = filmCrewUiModel.character ?: itemView.context.getString(R.string.unknown)
            ivPhotoActor.loadCropImage(filmCrewUiModel.photo)
        }
    }
}

class FilmCrewDiffUtil : DiffUtil.ItemCallback<FilmCrewUiModel>() {
    override fun areItemsTheSame(oldItem: FilmCrewUiModel, newItem: FilmCrewUiModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FilmCrewUiModel, newItem: FilmCrewUiModel): Boolean =
        oldItem == newItem
}
