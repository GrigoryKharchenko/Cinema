package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.entity.FilmViewedEntity
import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailModel
import kinopoisk.cinema.presentation.screen.homepage.TypeCardCategoryUiModel

fun List<FilmViewedEntity>.mapToFilmsViewedModel(): List<TypeCardCategoryUiModel> =
    this.map(FilmViewedEntity::mapToFilmViewedModel).plus(TypeCardCategoryUiModel.FooterUiModel())

fun FilmViewedEntity.mapToFilmViewedModel(): TypeCardCategoryUiModel.FilmUiModel =
    TypeCardCategoryUiModel.FilmUiModel(
        id = id,
        poster = poster,
        rating = rating,
        isViewed = isViewed,
        name = nameFilm,
        genre = genre,
        isVisibleRating = isVisibleRating
    )

fun TypeCardCategoryUiModel.FilmUiModel.mapToFilmViewedEntity(): FilmViewedEntity =
    FilmViewedEntity(
        id = id,
        poster = poster,
        rating = rating,
        isViewed = isViewed,
        nameFilm = name,
        genre = genre ?: "",
        isVisibleRating = isVisibleRating
    )
