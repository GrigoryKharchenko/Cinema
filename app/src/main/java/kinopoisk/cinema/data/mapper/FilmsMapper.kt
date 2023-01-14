package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.DetailFilmResponse
import kinopoisk.cinema.data.network.response.FilmResponse
import kinopoisk.cinema.data.network.response.FilmsResponse
import kinopoisk.cinema.extension.firstCharToUpperCase
import kinopoisk.cinema.presentation.screen.filmdetail.FilmDetailUiModel
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
        genre = genre.firstOrNull()?.genre?.firstCharToUpperCase(),
        isVisibleRating = ratingKinopoisk?.isNotEmpty() ?: rating?.isNotEmpty() ?: false,
    )

fun DetailFilmResponse.mapToDetailFilmModel(): FilmDetailUiModel =
    FilmDetailUiModel(
        id = id,
        poster = poster,
        logo = logo ?: "",
        shortDescription = shortDescription ?: "",
        description = description,
        detailFilm = "$rating " + (nameRu ?: nameEn ?: nameOriginal) +
                "\n${released ?: ""} " + (genre.joinToString { it.genre.firstCharToUpperCase() }) +
                "\n${countries.joinToString { countries -> countries.country }}, " + "${duration ?: ""} " + (ratingAgeLimits ?: ""),
        isVisibleShortDescription = shortDescription != null,
        name = nameRu ?: nameEn ?: nameOriginal
    )
