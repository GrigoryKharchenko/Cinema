package kinopoisk.cinema.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import kinopoisk.cinema.R

fun ImageView.setGlideImage(image: String, @DrawableRes placeholder: Int = R.drawable.preview_background) {
    Glide.with(this)
        .load(image)
        .placeholder(placeholder)
        .centerCrop()
        .into(this)
}
