package kinopoisk.cinema.presentation.screen.welcome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kinopoisk.cinema.databinding.ItemWelcomeBinding

class WelcomeViewPager : RecyclerView.Adapter<WelcomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeViewHolder =
        WelcomeViewHolder(
            ItemWelcomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = TypeWelcomePage.values().size

    override fun onBindViewHolder(holder: WelcomeViewHolder, position: Int) =
        holder.bind(TypeWelcomePage.values()[position])
}

class WelcomeViewHolder(private val binding: ItemWelcomeBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(typeWelcomePage: TypeWelcomePage) {
        with(binding) {
            tvDescription.text = itemView.context.getString(typeWelcomePage.title)
            image.setImageResource(typeWelcomePage.print)
        }
    }
}
