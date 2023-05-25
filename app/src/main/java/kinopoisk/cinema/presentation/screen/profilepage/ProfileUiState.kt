package kinopoisk.cinema.presentation.screen.profilepage

import kinopoisk.cinema.presentation.screen.homepage.TypeCardCategoryUiModel

sealed interface ProfileUiState {

    data class Success(
        val filmViewed: List<TypeCardCategoryUiModel> = emptyList(),
        val filmInteresting: List<TypeCardCategoryUiModel> = emptyList(),
        val countViewedFilm: String = "0",
        val countInterestingFilm: String = "0"
    ) : ProfileUiState
}
