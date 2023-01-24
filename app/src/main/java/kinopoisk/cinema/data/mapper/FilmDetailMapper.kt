package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.network.response.DetailFilmResponse
import kinopoisk.cinema.data.network.response.GalleryResponse
import kinopoisk.cinema.data.network.response.ImageResponse
import kinopoisk.cinema.data.network.response.SimilarResponse
import kinopoisk.cinema.data.network.response.SimilarsResponse
import kinopoisk.cinema.data.network.response.StuffResponse
import kinopoisk.cinema.extension.firstCharToUpperCase
import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.GalleryModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.SimilarFilmModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.StuffModel

fun List<StuffResponse>.mapToStuffModel(): List<StuffModel> =
    this.map(StuffResponse::mapToStuffModel)

fun StuffResponse.mapToStuffModel(): StuffModel =
    StuffModel(
        id = id,
        name = nameRu ?: nameEn,
        photo = photo,
        character = character,
        profession = profession,
    )

fun GalleryResponse.mapToGalleryModel(): List<GalleryModel> =
    this.items.map(ImageResponse::mapToImageModel)

fun ImageResponse.mapToImageModel(): GalleryModel =
    GalleryModel(
        image = image,
    )

fun SimilarsResponse.mapToSimilarsFilmsModel(): List<SimilarFilmModel> =
    this.items.map(SimilarResponse::mapToSimilarFilmModel)

fun SimilarResponse.mapToSimilarFilmModel(): SimilarFilmModel =
    SimilarFilmModel(
        id = id,
        name = nameRu ?: nameEn,
        poster = poster,
    )

fun DetailFilmResponse.mapToDetailFilmModel(): FilmDetailModel =
    FilmDetailModel(
        id = id,
        poster = poster,
        logo = logo ?: "",
        shortDescription = shortDescription ?: "",
        description = description,
        detailFilm = mapToDetailFilm(),
        isVisibleShortDescription = shortDescription != null,
        name = nameRu ?: nameEn ?: nameOriginal,
    )

fun DetailFilmResponse.mapToDetailFilm(): String {
    val countries = countries.joinToString { countries -> countries.country }
    val genres = genres.joinToString { it.genre.firstCharToUpperCase() }
    val released = if (released == null) "" else "$released,"
    val finalCounty = if (ratingAgeLimits == null && duration == null) countries else "$countries,"
    val duration = if (ratingAgeLimits == null) "${duration ?: ""} " else "$duration мин,"
    val rating = rating ?: ""
    val name = nameRu ?: nameEn ?: nameOriginal
    val ratingAgeLimits = ratingAgeLimits ?: ""
    return "$rating $name\n$released $genres\n$finalCounty $duration $ratingAgeLimits"
}
