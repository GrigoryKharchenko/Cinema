package kinopoisk.cinema.domain.repository

import kinopoisk.cinema.data.entity.FilmInterestingEntity
import kinopoisk.cinema.data.entity.FilmViewedEntity
import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.GalleryModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.SimilarFilmModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.StaffModel

interface DetailFilmRepository {

    suspend fun getFilmDetailModel(id: Int): Result<FilmDetailModel>

    suspend fun getGalleryModel(id: Int): Result<List<GalleryModel>>

    suspend fun getSimilarFilmModel(id: Int): Result<List<SimilarFilmModel>>

    suspend fun getStaffModel(id: Int): Result<List<StaffModel>>

    suspend fun getFilmViewed(id: Int): Result<FilmViewedEntity>

    suspend fun getFilmInteresting(id: Int): Result<FilmInterestingEntity>
}
