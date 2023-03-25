package kinopoisk.cinema.presentation.screen.season

data class SerialUiModel(
    val countSeason: Int,
    val currentSeason: String,
    val countEpisodesInSeasons: Int,
    val episodes: List<EpisodesModel>
)
