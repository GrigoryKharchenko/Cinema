package kinopoisk.cinema.presentation.screen.period.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemYearBinding
import kinopoisk.cinema.extension.inflate
import kinopoisk.cinema.presentation.screen.period.YearModel

class PeriodAdapter(
    private val onYearClick: (Int) -> Unit,
) :
    ListAdapter<YearModel, PeriodViewHolder>(PeriodDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeriodViewHolder =
        PeriodViewHolder(ItemYearBinding.bind(parent.inflate(R.layout.item_year)))

    override fun onBindViewHolder(holder: PeriodViewHolder, position: Int) {
        holder.bind(getItem(position), onYearClick)
    }
}

class PeriodViewHolder(private val binding: ItemYearBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        yearModel: YearModel,
        onYearClick: (Int) -> Unit,
    ) {
        with(binding) {
            tvYear.text = yearModel.year.toString()
            tvYear.setOnClickListener {
                onYearClick(yearModel.year)
            }
            if (yearModel.isSelected) {
                selectedBackground()
            } else {
                defaultBackground()
            }
        }
    }

    private fun defaultBackground() {
        binding.root.background = itemView.context.getDrawable(R.drawable.bg_unselected_year)
        binding.tvYear.setTextColor(itemView.context.getColor(R.color.black_text_color))
    }

    private fun selectedBackground() {
        binding.root.background = itemView.context.getDrawable(R.drawable.bg_selected_year)
        binding.tvYear.setTextColor(itemView.context.getColor(R.color.white_text_color))
    }
}

class PeriodDiffUtil : DiffUtil.ItemCallback<YearModel>() {

    override fun areItemsTheSame(oldItem: YearModel, newItem: YearModel): Boolean =
        oldItem.year == newItem.year

    override fun areContentsTheSame(oldItem: YearModel, newItem: YearModel): Boolean =
        oldItem == newItem
}
