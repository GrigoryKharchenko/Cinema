package kinopoisk.cinema.presentation.screen.filmdetail

import kinopoisk.cinema.presentation.screen.filmdetail.adpters.filmcrew.FilmCrewUiModel
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.gallery.GalleryUiModel
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.similar.SimilarUiModel

sealed interface FilmDetailUiState {

    object Error : FilmDetailUiState
    object Loading : FilmDetailUiState
    data class Success(
        val detailFilm: FilmDetailUiModel,
        val actor: List<FilmCrewUiModel>,
        val gallery: List<GalleryUiModel>,
        val similar: List<SimilarUiModel>,
        val filmCrew: List<FilmCrewUiModel>
    ) : FilmDetailUiState
}
