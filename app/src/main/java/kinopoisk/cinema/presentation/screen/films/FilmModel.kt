package kinopoisk.cinema.presentation.screen.films

data class FilmModel(
    val id: Int,
    val poster: String,
    val rating: String,
    val isViewed: Boolean,
    val name: String,
    val genre: String?,
    val isVisibleRating: Boolean,
)
