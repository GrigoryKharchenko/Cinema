package kinopoisk.cinema.presentation.screen.welcome

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kinopoisk.cinema.R

enum class TypeWelcomePage(
    @StringRes val title: Int,
    @DrawableRes val print: Int
) {
    PREMIERE(title = R.string.welcome_premiere, print = R.drawable.il_welcome_page_1),
    COLLECTION(title = R.string.welcome_create_collection, print = R.drawable.il_welcome_page_2),
    FRIENDS(title = R.string.welcome_share_friends, print = R.drawable.il_welcome_page_3),
}
