package kinopoisk.cinema.presentation.screen.searchfilter.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemCountryOrGenreBinding
import kinopoisk.cinema.extension.inflate
import kinopoisk.cinema.presentation.screen.searchfilter.SearchFilterModel

class SearchFilterAdapter
    : ListAdapter<SearchFilterModel, SearchFilterViewHolder>(SearchFilterDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFilterViewHolder =
        SearchFilterViewHolder(ItemCountryOrGenreBinding.bind(parent.inflate(R.layout.item_country_or_genre)))

    override fun onBindViewHolder(holder: SearchFilterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SearchFilterViewHolder(private val binding: ItemCountryOrGenreBinding) : ViewHolder(binding.root) {
    fun bind(
        searchFilterModel: SearchFilterModel
    ) {
        binding.tvCountryOrGenre.text = searchFilterModel.countryOrGenre
    }
}

class SearchFilterDiffUtil : DiffUtil.ItemCallback<SearchFilterModel>() {
    override fun areItemsTheSame(oldItem: SearchFilterModel, newItem: SearchFilterModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SearchFilterModel, newItem: SearchFilterModel): Boolean =
        oldItem == newItem
}
