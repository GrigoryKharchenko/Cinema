package kinopoisk.cinema.presentation.screen.films

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemCertainCategoryBinding
import kinopoisk.cinema.extension.inflate
import kinopoisk.cinema.extension.loadCropImage

class FilmsAdapter(
    private val onOpenFilm: (Int) -> Unit,
) : PagingDataAdapter<FilmModel, FilmsViewHolder>(FilmsDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder =
        FilmsViewHolder(ItemCertainCategoryBinding.bind(parent.inflate(R.layout.item_certain_category)))

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onOpenFilm) }
    }
}

class FilmsViewHolder(private val binding: ItemCertainCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        filmModel: FilmModel,
        onOpenFilm: (Int) -> Unit,
    ) {
        with(binding) {
            tvRating.text = filmModel.rating
            ivPreview.loadCropImage(filmModel.poster)
            tvName.text = filmModel.name
            tvGenre.text = filmModel.genre
            ivViewed.isVisible = filmModel.isViewed
            tvRating.isVisible = filmModel.isVisibleRating
            root.setOnClickListener {
                onOpenFilm(filmModel.id)
            }
        }
    }
}

class FilmsDiffUtil : DiffUtil.ItemCallback<FilmModel>() {
    override fun areItemsTheSame(oldItem: FilmModel, newItem: FilmModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FilmModel, newItem: FilmModel): Boolean =
        oldItem == newItem

}
