package kinopoisk.cinema.presentation.screen.filmdetail.adpters.staff

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemStaffBinding
import kinopoisk.cinema.extension.inflate
import kinopoisk.cinema.extension.loadCropImage
import kinopoisk.cinema.presentation.screen.filmdetail.model.StaffModel

class StaffAdapter : ListAdapter<StaffModel, StaffViewHolder>(StaffDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder =
        StaffViewHolder(ItemStaffBinding.bind(parent.inflate(R.layout.item_staff)))

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class StaffViewHolder(private val binding: ItemStaffBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(staffModel: StaffModel,) {
        with(binding) {
            tvNameActor.text = staffModel.name ?: itemView.context.getString(R.string.unknown)
            tvActorCharacter.text = staffModel.character ?: itemView.context.getString(R.string.unknown)
            ivPhotoActor.loadCropImage(staffModel.photo)
        }
    }
}

class StaffDiffUtil : DiffUtil.ItemCallback<StaffModel>() {
    override fun areItemsTheSame(oldItem: StaffModel, newItem: StaffModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: StaffModel, newItem: StaffModel): Boolean =
        oldItem == newItem
}
