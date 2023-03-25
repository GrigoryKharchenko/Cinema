package kinopoisk.cinema.presentation.screen.season

sealed interface SeasonUiState {

    object Loading : SeasonUiState

    object Error : SeasonUiState

    data class Success(
        val countSeason: Int,
        val currentSeason: String,
        val countEpisodesInSeasons: Int,
        val episodes: List<EpisodesModel>,
        val isFirstSeason: Boolean,
    ) : SeasonUiState
}
