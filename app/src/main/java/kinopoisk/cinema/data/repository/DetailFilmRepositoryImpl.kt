package kinopoisk.cinema.data.repository

import kinopoisk.cinema.data.mapper.mapToDetailFilmModel
import kinopoisk.cinema.data.mapper.mapToFilmCrewsModel
import kinopoisk.cinema.data.mapper.mapToGalleryModel
import kinopoisk.cinema.data.mapper.mapToSimilarsModel
import kinopoisk.cinema.data.network.KinopoiskApi
import kinopoisk.cinema.domain.DetailFilmRepository
import kinopoisk.cinema.presentation.screen.filmdetail.FilmDetailUiModel
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.filmcrew.FilmCrewUiModel
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.gallery.GalleryUiModel
import kinopoisk.cinema.presentation.screen.filmdetail.adpters.similar.SimilarUiModel
import javax.inject.Inject

class DetailFilmRepositoryImpl @Inject constructor(
    private val kinopoiskApi: KinopoiskApi
) : DetailFilmRepository {

    override suspend fun getFilmDetail(id: Int): FilmDetailUiModel =
        kinopoiskApi.getDetailFilm(id).mapToDetailFilmModel()

    override suspend fun getActor(id: Int): List<FilmCrewUiModel> =
        kinopoiskApi.getFilmCrew(id).mapToFilmCrewsModel()

    override suspend fun getGallery(id: Int): List<GalleryUiModel> =
        kinopoiskApi.getGallery(id).mapToGalleryModel()

    override suspend fun getSimilar(id: Int): List<SimilarUiModel> =
        kinopoiskApi.getSimilars(id).mapToSimilarsModel()

    override suspend fun getFilmCrew(id: Int): List<FilmCrewUiModel> =
        kinopoiskApi.getFilmCrew(id).mapToFilmCrewsModel()
}
