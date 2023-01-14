package kinopoisk.cinema.presentation.screen.filmdetail.adpters.gallery

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemGalleryBinding
import kinopoisk.cinema.extension.inflate
import kinopoisk.cinema.extension.loadCropImage

class GalleryAdapter : ListAdapter<GalleryUiModel, GalleryViewHolder>(GalleryDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder =
        GalleryViewHolder(ItemGalleryBinding.bind(parent.inflate(R.layout.item_gallery)))

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class GalleryViewHolder(private val binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(galleryUiModel: GalleryUiModel) {
        with(binding) {
            ivGallery.loadCropImage(galleryUiModel.image)
        }
    }
}

class GalleryDiffUtil : DiffUtil.ItemCallback<GalleryUiModel>() {
    override fun areItemsTheSame(oldItem: GalleryUiModel, newItem: GalleryUiModel): Boolean =
        oldItem.image == newItem.image

    override fun areContentsTheSame(oldItem: GalleryUiModel, newItem: GalleryUiModel): Boolean =
        oldItem == newItem

}
