package kinopoisk.cinema.domain

import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.StuffModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.GalleryModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.SimilarFilmModel

interface DetailFilmRepository {

    suspend fun getFilmDetail(id: Int): FilmDetailModel

    suspend fun getActor(id: Int): List<StuffModel>

    suspend fun getGallery(id: Int): List<GalleryModel>

    suspend fun getSimilar(id: Int): List<SimilarFilmModel>

    suspend fun getStuff(id: Int): List<StuffModel>

}
