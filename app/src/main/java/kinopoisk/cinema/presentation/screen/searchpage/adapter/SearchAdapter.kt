package kinopoisk.cinema.presentation.screen.searchpage.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemSearchFilmBinding
import kinopoisk.cinema.extension.inflate
import kinopoisk.cinema.extension.loadCropImage
import kinopoisk.cinema.presentation.screen.searchpage.SearchModel

class SearchAdapter(
    private val onOpenFilm: (Int) -> Unit,
) : PagingDataAdapter<SearchModel, SearchViewHolder>(SearchDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(ItemSearchFilmBinding.bind(parent.inflate(R.layout.item_search_film)))

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onOpenFilm) }
    }
}

class SearchViewHolder(private val binding: ItemSearchFilmBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        searchModel: SearchModel,
        onOpenFilm: (Int) -> Unit,
    ) {
        with(binding) {
            ivPoster.loadCropImage(searchModel.poster)
            tvNameFilm.text = searchModel.nameFilm
            tvRating.text = searchModel.rating
            tvYearAndGenre.text = searchModel.yearAndGenre
            root.setOnClickListener {
                onOpenFilm(searchModel.id)
            }
        }
    }
}

class SearchDiffUtil : DiffUtil.ItemCallback<SearchModel>() {
    override fun areItemsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean =
        oldItem == newItem
}
