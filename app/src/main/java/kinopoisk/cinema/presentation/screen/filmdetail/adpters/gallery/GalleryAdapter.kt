package kinopoisk.cinema.presentation.screen.filmdetail.adpters.gallery

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemGalleryBinding
import kinopoisk.cinema.extension.inflate
import kinopoisk.cinema.extension.loadCropImage
import kinopoisk.cinema.presentation.screen.filmdetail.model.GalleryModel

class GalleryAdapter : ListAdapter<GalleryModel, GalleryViewHolder>(GalleryDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder =
        GalleryViewHolder(ItemGalleryBinding.bind(parent.inflate(R.layout.item_gallery)))

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class GalleryViewHolder(private val binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(galleryModel: GalleryModel) {
        binding.ivGallery.loadCropImage(galleryModel.image)
    }
}

class GalleryDiffUtil : DiffUtil.ItemCallback<GalleryModel>() {
    override fun areItemsTheSame(oldItem: GalleryModel, newItem: GalleryModel): Boolean =
        oldItem.image == newItem.image

    override fun areContentsTheSame(oldItem: GalleryModel, newItem: GalleryModel): Boolean =
        oldItem == newItem

}
