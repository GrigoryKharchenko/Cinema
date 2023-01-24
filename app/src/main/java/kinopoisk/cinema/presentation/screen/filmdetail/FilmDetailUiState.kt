package kinopoisk.cinema.presentation.screen.filmdetail

import kinopoisk.cinema.presentation.screen.filmdetail.model.ErrorUiModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailUiModel

sealed interface FilmDetailUiState {

    data class Error(val errors: ErrorUiModel) : FilmDetailUiState
    object Loading : FilmDetailUiState
    data class Success(
        val filmDetailUiModel: FilmDetailUiModel
    ) : FilmDetailUiState
}
