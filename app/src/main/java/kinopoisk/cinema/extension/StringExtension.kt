package kinopoisk.cinema.extension

fun String.firstCharToUpperCase() = this.replaceFirstChar(Char::titlecase)

fun Any?.toStringOrEmpty() = this?.toString() ?: ""
