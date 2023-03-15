package kinopoisk.cinema.data.mapper

import kinopoisk.cinema.data.entity.FilmViewedEntity
import kinopoisk.cinema.data.network.response.DetailFilmResponse
import kinopoisk.cinema.data.network.response.GalleryResponse
import kinopoisk.cinema.data.network.response.ImageResponse
import kinopoisk.cinema.data.network.response.SimilarResponse
import kinopoisk.cinema.data.network.response.SimilarsResponse
import kinopoisk.cinema.data.network.response.StaffResponse
import kinopoisk.cinema.extension.firstCharToUpperCase
import kinopoisk.cinema.presentation.screen.filmdetail.model.FilmDetailModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.GalleryModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.SimilarFilmModel
import kinopoisk.cinema.presentation.screen.filmdetail.model.StaffModel

fun List<StaffResponse>.mapToStaffModel(): List<StaffModel> =
    this.map(StaffResponse::mapToStaffModel)

private fun StaffResponse.mapToStaffModel(): StaffModel =
    StaffModel(
        id = id,
        name = nameRu ?: nameEn,
        photo = photo,
        character = character ?: "",
        profession = profession,
    )

fun GalleryResponse.mapToGalleryModel(): List<GalleryModel> =
    this.items.map(ImageResponse::mapToImageModel)

private fun ImageResponse.mapToImageModel(): GalleryModel =
    GalleryModel(
        image = image,
    )

fun SimilarsResponse.mapToSimilarsFilmsModel(): List<SimilarFilmModel> =
    this.items.map(SimilarResponse::mapToSimilarFilmModel)

private fun SimilarResponse.mapToSimilarFilmModel(): SimilarFilmModel =
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

fun DetailFilmResponse.mapToFilmViewedEntity(): FilmViewedEntity =
    FilmViewedEntity(
        id = id,
        poster = poster,
        rating = rating ?: "",
        isViewed = true,
        nameFilm = nameRu ?: nameEn ?: nameOriginal,
        genre = genres.first().genre.firstCharToUpperCase(),
        isVisibleRating = rating?.isNotEmpty() ?: false
    )

private fun DetailFilmResponse.mapToDetailFilm(): String {
fun DetailFilmResponse.mapToDetailFilm(): String {
    val countries = countries.joinToString { countries -> countries.country }
    val genres = genres.joinToString { it.genre.firstCharToUpperCase() }
    val name = nameRu ?: nameEn ?: nameOriginal
    val duration = if (duration != null) "$duration мин" else ""
    val ratingAge = ratingAgeLimits ?: ratingMpaa
    val firstPart = listOfNotNull(rating, name).joinToString(" ")
    val secondPart = listOfNotNull(released, genres).joinToString()
    val thirdPart = listOfNotNull(countries, duration, ratingAge).joinToString()

    return "$firstPart\n$secondPart\n$thirdPart"
}
