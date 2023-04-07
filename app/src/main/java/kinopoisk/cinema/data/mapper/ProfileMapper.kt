package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.entity.FilmInterestingEntity
import kinopoisk.cinema.data.entity.FilmViewedEntity
import kinopoisk.cinema.presentation.screen.homepage.TypeCardCategoryUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

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

fun List<FilmInterestingEntity>.mapToFilmInteresting(): List<TypeCardCategoryUiModel> =
    this.map(FilmInterestingEntity::mapToFilmInteresting).plus(TypeCardCategoryUiModel.FooterUiModel())

fun FilmInterestingEntity.mapToFilmInteresting(): TypeCardCategoryUiModel.FilmUiModel =
    TypeCardCategoryUiModel.FilmUiModel(
        id = id,
        poster = poster,
        rating = rating,
        isViewed = isViewed,
        name = nameFilm,
        genre = genre,
        isVisibleRating = isVisibleRating
    )

fun TypeCardCategoryUiModel.FilmUiModel.mapToFilmInteresting(): FilmInterestingEntity =
    FilmInterestingEntity(
        id = id,
        poster = poster,
        rating = rating,
        isViewed = isViewed,
        nameFilm = name,
        genre = genre ?: "",
        isVisibleRating = isVisibleRating
    )
