package kinopoisk.cinema.data

import java.io.Serializable

sealed class TypeCategories(
    open val nameCategory: String
) : Serializable {

    data class Premieres(
        override val nameCategory: String,
    ) : TypeCategories(nameCategory)

    data class Top(
        override val nameCategory: String,
        val type: String,
    ) : TypeCategories(nameCategory)

    data class Random(
        override val nameCategory: String,
        val countryCode: String,
        val genresCode: String,
    ) : TypeCategories(nameCategory)

    data class Serials(
        override val nameCategory: String,
        val type: String,
    ) : TypeCategories(nameCategory)
}
