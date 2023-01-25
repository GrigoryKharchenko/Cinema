package kinopoisk.cinema.domain

import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.StaffModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.GalleryModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.SimilarFilmModel

interface DetailFilmRepository {

    suspend fun getFilmDetail(id: Int): FilmDetailModel

    suspend fun getActor(id: Int): List<StaffModel>

    suspend fun getGallery(id: Int): List<GalleryModel>

    suspend fun getSimilar(id: Int): List<SimilarFilmModel>

    suspend fun getStaff(id: Int): List<StaffModel>

}
