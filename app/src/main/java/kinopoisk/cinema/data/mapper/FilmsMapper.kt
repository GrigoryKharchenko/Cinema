package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.FilmResponse
import kinopoisk.cinema.data.network.response.FilmsResponse
import kinopoisk.cinema.extension.firstCharToUpperCase
import kinopoisk.cinema.presentation.screen.films.FilmModel
import kinopoisk.cinema.presentation.screen.homepage.TypeCardCategoryUiModel

private const val MAX_FILMS = 20

fun FilmsResponse.mapToDifferentFilmsModel(genre: String): List<TypeCardCategoryUiModel> =
    this.anotherFilms.take(MAX_FILMS).map {
        it.mapToFilmModel(genre)
    }.plus(TypeCardCategoryUiModel.FooterUiModel())

fun FilmsResponse.mapToDifferentFilmsModel(): List<TypeCardCategoryUiModel> =
    this.anotherFilms.take(MAX_FILMS).map(FilmResponse::mapToFilmModel).plus(TypeCardCategoryUiModel.FooterUiModel())

fun FilmsResponse.mapToTopFilmsModel(): List<TypeCardCategoryUiModel> =
    this.topFilms.take(MAX_FILMS).map(FilmResponse::mapToTopFilmModel).plus(TypeCardCategoryUiModel.FooterUiModel())

fun FilmResponse.mapToFilmModel(genre: String): TypeCardCategoryUiModel =
    TypeCardCategoryUiModel.FilmUiModel(
        id = kinopoiskId ?: id,
        poster = poster,
        rating = ratingKinopoisk ?: rating ?: "",
        isViewed = false,
        name = nameRu ?: nameEn ?: nameOriginal,
        genre = genre.firstCharToUpperCase(),
        isVisibleRating = ratingKinopoisk?.isNotEmpty() ?: rating?.isNotEmpty() ?: false,
    )

fun FilmResponse.mapToFilmModel(): TypeCardCategoryUiModel =
    TypeCardCategoryUiModel.FilmUiModel(
        id = kinopoiskId ?: id,
        poster = poster,
        rating = ratingKinopoisk ?: rating ?: "",
        isViewed = false,
        name = nameRu ?: nameEn ?: nameOriginal,
        genre = genres.firstOrNull()?.genre?.firstCharToUpperCase(),
        isVisibleRating = ratingKinopoisk?.isNotEmpty() ?: rating?.isNotEmpty() ?: false,
    )

fun FilmResponse.mapToTopFilmModel(): TypeCardCategoryUiModel =
    TypeCardCategoryUiModel.FilmUiModel(
        id = kinopoiskId ?: id,
        poster = poster,
        rating = ratingKinopoisk ?: rating ?: "",
        isViewed = false,
        name = nameRu ?: nameEn ?: nameOriginal,
        genre = genres.firstOrNull()?.genre?.firstCharToUpperCase(),
        isVisibleRating = ratingKinopoisk?.isNotEmpty() ?: rating?.isNotEmpty() ?: false,
    )

fun FilmsResponse.mapToAllFilmsModel(): List<FilmModel> =
    this.topFilms.map(FilmResponse::mapToFilmsModel)

fun FilmsResponse.mapToAllAnotherFilmsModel(): List<FilmModel> =
    this.anotherFilms.map(FilmResponse::mapToFilmsModel)

fun FilmResponse.mapToFilmsModel(): FilmModel =
    FilmModel(
        id = kinopoiskId ?: id,
        poster = poster,
        rating = ratingKinopoisk ?: rating ?: "",
        isViewed = false,
        name = nameRu ?: nameEn ?: nameOriginal,
        genre = genres.firstOrNull()?.genre?.firstCharToUpperCase(),
        isVisibleRating = ratingKinopoisk?.isNotEmpty() ?: rating?.isNotEmpty() ?: false,
    )
