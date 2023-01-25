package kinopoisk.cinema.data.repository

import kinopoisk.cinema.data.mapper.mapToDetailFilmModel
import kinopoisk.cinema.data.mapper.mapToStaffModel
import kinopoisk.cinema.data.mapper.mapToGalleryModel
import kinopoisk.cinema.data.mapper.mapToSimilarsFilmsModel
import kinopoisk.cinema.data.network.KinopoiskApi
import kinopoisk.cinema.domain.DetailFilmRepository
import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.GalleryModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.SimilarFilmModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.StaffModel
import javax.inject.Inject

class DetailFilmRepositoryImpl @Inject constructor(
    private val kinopoiskApi: KinopoiskApi,
) : DetailFilmRepository {

    override suspend fun getFilmDetail(id: Int): FilmDetailModel =
        kinopoiskApi.getDetailFilm(id).mapToDetailFilmModel()

    override suspend fun getActor(id: Int): List<StaffModel> =
        kinopoiskApi.getStaff(id).mapToStaffModel()

    override suspend fun getGallery(id: Int): List<GalleryModel> =
        kinopoiskApi.getGallery(id).mapToGalleryModel()

    override suspend fun getSimilar(id: Int): List<SimilarFilmModel> =
        kinopoiskApi.getSimilars(id).mapToSimilarsFilmsModel()

    override suspend fun getStaff(id: Int): List<StaffModel> =
        kinopoiskApi.getStaff(id).mapToStaffModel()
}
