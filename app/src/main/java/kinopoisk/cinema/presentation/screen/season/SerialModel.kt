package kinopoisk.cinema.presentation.screen.season

data class SerialModel(
    val countSeasons: Int,
    val seasons: List<SeasonsModel>
)

data class SeasonsModel(
    val numberSeason: String,
    val episodes: List<EpisodesModel>,
)

data class EpisodesModel(
    val seasonNumber: String,
    val episodeNumber: String,
    val nameSeries: String,
    val releaseDate: String
)
