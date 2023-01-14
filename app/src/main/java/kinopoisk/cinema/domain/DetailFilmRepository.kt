package kinopoisk.cinema.domain

import kinopoisk.cinema.presentation.screen.filmdetail.FilmDetailUiModel
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.filmcrew.FilmCrewUiModel
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.gallery.GalleryUiModel
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.similar.SimilarUiModel

interface DetailFilmRepository {

    suspend fun getFilmDetail(id: Int): FilmDetailUiModel

    suspend fun getActor(id: Int): List<FilmCrewUiModel>

    suspend fun getGallery(id: Int): List<GalleryUiModel>

    suspend fun getSimilar(id: Int): List<SimilarUiModel>

    suspend fun getFilmCrew(id: Int): List<FilmCrewUiModel>

}
