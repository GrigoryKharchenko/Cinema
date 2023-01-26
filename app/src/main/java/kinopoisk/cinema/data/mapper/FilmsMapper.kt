package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.FilmResponse
import kinopoisk.cinema.data.network.response.FilmsResponse
import kinopoisk.cinema.extension.firstCharToUpperCase
import kinopoisk.cinema.presentation.screen.homepage.TypeCardCategoryUiModel

fun FilmsResponse.mapToDifferentFilmsModel(): List<TypeCardCategoryUiModel> =
    this.anotherFilms.map(FilmResponse::mapToFilmModel)

fun FilmsResponse.mapToTopFilmsModel(): List<TypeCardCategoryUiModel> =
    this.topFilms.map(FilmResponse::mapToFilmModel)

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
