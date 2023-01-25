package kinopoisk.cinema.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class TypeCategories(
    open val nameCategory: String
) : Parcelable {

    @Parcelize
    data class Premieres(
        override val nameCategory: String,
    ) : TypeCategories(nameCategory)

    @Parcelize
    data class Top(
        override val nameCategory: String,
        val type: String,
    ) : TypeCategories(nameCategory)

    @Parcelize
    data class Random(
        override val nameCategory: String,
        val countryCode: String,
        val genresCode: String,
    ) : TypeCategories(nameCategory)

    @Parcelize
    data class Serials(
        override val nameCategory: String,
        val type: String,
    ) : TypeCategories(nameCategory)
}
