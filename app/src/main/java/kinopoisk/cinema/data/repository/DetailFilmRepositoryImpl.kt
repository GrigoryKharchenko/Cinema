package kinopoisk.cinema.data.repository

import kinopoisk.cinema.data.entity.FilmViewedEntity
import kinopoisk.cinema.data.mapper.mapToDetailFilmModel
import kinopoisk.cinema.data.mapper.mapToFilmViewedEntity
import kinopoisk.cinema.data.mapper.mapToGalleryModel
import kinopoisk.cinema.data.mapper.mapToSerialModel
import kinopoisk.cinema.data.mapper.mapToSimilarsFilmsModel
import kinopoisk.cinema.data.mapper.mapToStaffModel
import kinopoisk.cinema.data.network.KinopoiskApi
import kinopoisk.cinema.domain.repository.DetailFilmRepository
import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.GalleryModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.SimilarFilmModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.StaffModel
import kinopoisk.cinema.presentation.screen.season.SerialModel
import javax.inject.Inject

class DetailFilmRepositoryImpl @Inject constructor(
    private val kinopoiskApi: KinopoiskApi,
) : DetailFilmRepository {

    override suspend fun getFilmDetailModel(id: Int): Result<FilmDetailModel> =
        runCatching {
            kinopoiskApi.getDetailFilm(id).mapToDetailFilmModel()
        }

    override suspend fun getGalleryModel(id: Int): Result<List<GalleryModel>> =
        runCatching {
            kinopoiskApi.getGallery(id).mapToGalleryModel()
        }

    override suspend fun getSimilarFilmModel(id: Int): Result<List<SimilarFilmModel>> =
        runCatching {
            kinopoiskApi.getSimilars(id).mapToSimilarsFilmsModel()
        }

    override suspend fun getStaffModel(id: Int): Result<List<StaffModel>> =
        runCatching {
            kinopoiskApi.getStaff(id).mapToStaffModel()
        }

    override suspend fun getFilmViewedEntity(id: Int): Result<FilmViewedEntity> =
        runCatching {
            kinopoiskApi.getDetailFilm(id).mapToFilmViewedEntity()
        }

    override suspend fun getSerial(id: Int): Result<SerialModel> =
        runCatching {
            kinopoiskApi.getSerial(id).mapToSerialModel()
        }
}
