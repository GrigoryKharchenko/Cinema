package kinopoisk.cinema.presentation.screen.filmdetail.model

data class FilmDetailModel(
    val id: Int,
    val poster: String,
    val logo: String,
    val name: String,
    val detailFilm: String,
    val shortDescription: String,
    val description: String,
    val isVisibleShortDescription: Boolean,
    val isVisibleSeason: Boolean,
)
