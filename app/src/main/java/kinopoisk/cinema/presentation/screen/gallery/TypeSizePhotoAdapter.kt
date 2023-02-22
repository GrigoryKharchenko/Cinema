package kinopoisk.cinema.presentation.screen.gallery

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kinopoisk.cinema.R
import kinopoisk.cinema.databinding.ItemBigPhotoBinding
import kinopoisk.cinema.databinding.ItemSmallPhotoBinding
import kinopoisk.cinema.extension.inflate
import kinopoisk.cinema.extension.loadCropImage

class TypeSizePhotoAdapter : ListAdapter<TypeSizePhotoUiModel, TypeSizePhotoViewHolder>(TypeSizePhotoDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeSizePhotoViewHolder {
        return when (viewType) {
            R.layout.item_small_photo -> TypeSizePhotoViewHolder.SmallPhotoViewHolder(
                ItemSmallPhotoBinding.bind(parent.inflate(R.layout.item_small_photo))
            )

            R.layout.item_big_photo -> TypeSizePhotoViewHolder.BigPhotoViewHolder(
                ItemBigPhotoBinding.bind(parent.inflate(R.layout.item_big_photo))
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: TypeSizePhotoViewHolder, position: Int) {
        when (holder) {
            is TypeSizePhotoViewHolder.SmallPhotoViewHolder ->
                holder.bind(getItem(position) as TypeSizePhotoUiModel.SmallPhoto)

            is TypeSizePhotoViewHolder.BigPhotoViewHolder ->
                holder.bind(getItem(position) as TypeSizePhotoUiModel.BigPhoto)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TypeSizePhotoUiModel.BigPhoto ->
                R.layout.item_big_photo
            else ->
                R.layout.item_small_photo
        }
    }
}

sealed class TypeSizePhotoViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class SmallPhotoViewHolder(
        private val binding: ItemSmallPhotoBinding
    ) : TypeSizePhotoViewHolder(binding) {
        fun bind(
            smallPhoto: TypeSizePhotoUiModel.SmallPhoto
        ) {
            binding.ivFirstPhoto.loadCropImage(image = smallPhoto.firstPhoto)
            binding.ivSecondPhoto.loadCropImage(image = smallPhoto.secondPhoto)
        }
    }

    class BigPhotoViewHolder(
        private val binding: ItemBigPhotoBinding
    ) : TypeSizePhotoViewHolder(binding) {
        fun bind(
            bigPhoto: TypeSizePhotoUiModel.BigPhoto
        ) {
            binding.ivBigPhoto.loadCropImage(image = bigPhoto.bigPhoto)
        }
    }
}

class TypeSizePhotoDiffUtil : DiffUtil.ItemCallback<TypeSizePhotoUiModel>() {

    override fun areItemsTheSame(oldItem: TypeSizePhotoUiModel, newItem: TypeSizePhotoUiModel): Boolean {
        return if (oldItem is TypeSizePhotoUiModel.SmallPhoto && newItem is TypeSizePhotoUiModel.SmallPhoto)
            oldItem.firstPhoto == newItem.secondPhoto
        else
            true
    }

    override fun areContentsTheSame(oldItem: TypeSizePhotoUiModel, newItem: TypeSizePhotoUiModel): Boolean =
        oldItem == newItem
}
