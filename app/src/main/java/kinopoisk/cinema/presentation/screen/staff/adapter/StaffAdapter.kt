package kinopoisk.cinema.presentation.screen.staff.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemAllStaffBinding
import kinopoisk.cinema.extension.inflate
import kinopoisk.cinema.extension.loadCropImage
import kinopoisk.cinema.presentation.screen.filmdetail.model.StaffModel

class StaffAdapter(private val onStaffClick: (Int) -> Unit) :
    ListAdapter<StaffModel, StaffViewHolder>(StaffDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder =
        StaffViewHolder(ItemAllStaffBinding.bind(parent.inflate(R.layout.item_all_staff)))

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) =
        holder.bind(getItem(position), onStaffClick)
}

class StaffViewHolder(private val binding: ItemAllStaffBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        staffModel: StaffModel,
        onStaffClick: (Int) -> Unit
    ) {
        with(binding) {
            tvNameStaff.text = staffModel.name ?: itemView.context.getString(R.string.unknown)
            tvProfession.text = staffModel.character ?: itemView.context.getString(R.string.unknown)
            ivPhotoStaff.loadCropImage(staffModel.photo)
            root.setOnClickListener {
                onStaffClick(staffModel.id)
            }
        }
    }
}

class StaffDiffUtil : DiffUtil.ItemCallback<StaffModel>() {
    override fun areItemsTheSame(oldItem: StaffModel, newItem: StaffModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: StaffModel, newItem: StaffModel): Boolean =
        oldItem == newItem
}
