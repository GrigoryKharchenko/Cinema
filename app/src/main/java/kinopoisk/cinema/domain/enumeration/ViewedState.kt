package kinopoisk.cinema.domain.enumeration

import androidx.annotation.DrawableRes
import kinopoisk.cinema.R

enum class ViewedState(@DrawableRes val viewedState: Int) {
    VIEWED(R.drawable.ic_viewed_gray),
    DONT_VIEWED(R.drawable.ic_dont_viewed)
}
