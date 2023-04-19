package kinopoisk.cinema.presentation.screen.season

data class SerialModel(
    val countSeasons: Int,
    val seasons: List<SeasonsModel>
)

data class SeasonsModel(
    val numberSeason: Int,
    val episodes: List<EpisodesModel>,
)

data class EpisodesModel(
    val seasonNumber: Int,
    val episodeNumber: Int,
    val nameSeries: String,
    val releaseDate: String
)
