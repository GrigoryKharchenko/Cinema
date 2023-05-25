package kinopoisk.cinema.presentation.screen.season.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemEpisodesBinding
import kinopoisk.cinema.extension.inflate
import kinopoisk.cinema.presentation.screen.season.EpisodesModel

class SeasonAdapter : ListAdapter<EpisodesModel, SeasonViewHolder>(SeasonDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder =
        SeasonViewHolder(ItemEpisodesBinding.bind(parent.inflate(R.layout.item_episodes)))

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class SeasonViewHolder(private val binding: ItemEpisodesBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(episodesModel: EpisodesModel) {
        with(binding) {
            tvNumberSeries.text = itemView.context.getString(
                R.string.season_title_number_serial,
                episodesModel.episodeNumber,
                episodesModel.nameSeries
            )
            tvReleaseDate.text = episodesModel.releaseDate
        }
    }
}

class SeasonDiffUtil : DiffUtil.ItemCallback<EpisodesModel>() {

    override fun areItemsTheSame(oldItem: EpisodesModel, newItem: EpisodesModel): Boolean =
        oldItem.episodeNumber == newItem.episodeNumber

    override fun areContentsTheSame(oldItem: EpisodesModel, newItem: EpisodesModel): Boolean =
        oldItem == newItem
}
