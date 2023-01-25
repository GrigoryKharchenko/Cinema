package kinopoisk.cinema.presentation.screen.filmdetail

import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailUiModel

sealed interface FilmDetailUiState {

    object Loading : FilmDetailUiState
    data class Content(
        val filmDetailUiModel: FilmDetailUiModel
    ) : FilmDetailUiState
}
