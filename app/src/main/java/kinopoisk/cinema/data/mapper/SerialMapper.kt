package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.EpisodesResponse
import kinopoisk.cinema.data.network.response.SeasonsResponse
import kinopoisk.cinema.data.network.response.SerialsResponse
import kinopoisk.cinema.presentation.screen.season.EpisodesModel
import kinopoisk.cinema.presentation.screen.season.SeasonUiState
import kinopoisk.cinema.presentation.screen.season.SeasonsModel
import kinopoisk.cinema.presentation.screen.season.SerialModel
import kotlinx.coroutines.flow.MutableStateFlow

fun SerialsResponse.mapToSerialModel(): SerialModel =
    SerialModel(
        countSeasons = countSeason,
        seasons = mapToSeasons()
    )

fun SerialsResponse.mapToSeasons(): List<SeasonsModel> =
    this.items.map(SeasonsResponse::mapToSeasonModel)

fun SeasonsResponse.mapToSeasonModel(): SeasonsModel =
    SeasonsModel(
        numberSeason = numberSeason,
        episodes = mapToEpisodes(),
    )

fun SeasonsResponse.mapToEpisodes(): List<EpisodesModel> =
    this.episodes.map(EpisodesResponse::mapToEpisodeModel)

fun EpisodesResponse.mapToEpisodeModel(): EpisodesModel =
    EpisodesModel(
        seasonNumber = seasonNumber,
        episodeNumber = if (episodeNumber == "0") "1" else episodeNumber,
        nameSeries = nameRu ?: nameEn ?: "",
        releaseDate = releaseDate
    )

suspend fun SerialModel.mapToSeason(
    numberSeason: String,
    state: MutableStateFlow<SeasonUiState>,
    isFirstSeason: Boolean
): SeasonsModel {
    val season = seasons.first {
        it.numberSeason == numberSeason
    }
    state.emit(
        SeasonUiState.Success(
            countSeason = this.countSeasons,
            currentSeason = season.numberSeason,
            countEpisodesInSeasons = season.episodes.size,
            episodes = season.episodes,
            isFirstSeason = isFirstSeason
        )
    )
    return season
}
