package kinopoisk.cinema.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kinopoisk.cinema.R

fun ImageView.loadCropImage(image: String, @DrawableRes placeholder: Int = R.drawable.preview_background) {
    Glide.with(this)
        .load(image)
        .placeholder(placeholder)
        .transform(CenterCrop(), RoundedCorners(10))
        .into(this)
}

fun ImageView.loadImage(image: String, @DrawableRes placeholder: Int = R.drawable.preview_background) {
    Glide.with(this)
        .load(image)
        .placeholder(placeholder)
        .into(this)
}
