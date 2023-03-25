package kinopoisk.cinema.presentation.screen.filmdetail.model

import kinopoisk.cinema.presentation.screen.season.SeasonsModel
import kinopoisk.cinema.presentation.screen.season.SerialModel

data class SerialUiModel(
    val countSeason: SerialModel,
    val countEpisodes: SeasonsModel
)
