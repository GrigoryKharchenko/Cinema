package kinopoisk.cinema.domain.reposittory

import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.GalleryModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.SimilarFilmModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.StaffModel

interface DetailFilmRepository {

    suspend fun getFilmDetail(id: Int): Result<FilmDetailModel>

    suspend fun getGallery(id: Int): Result<List<GalleryModel>>

    suspend fun getSimilar(id: Int): Result<List<SimilarFilmModel>>

    suspend fun getStaff(id: Int): Result<List<StaffModel>>
}
